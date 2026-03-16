package com.example.shrinidhi.utils

import android.content.Context
import com.example.shrinidhi.model.ScanHistoryItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ScanHistoryManager(private val context: Context) {

    private val prefs = context.getSharedPreferences("scan_history", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun addToHistory(item: ScanHistoryItem) {
        val list = getHistory().toMutableList()

        // keep only last 10
        if (list.size >= 10) list.removeAt(0)

        list.add(item)

        val json = gson.toJson(list)
        prefs.edit().putString("history", json).apply()
    }

    fun getHistory(): List<ScanHistoryItem> {
        val json = prefs.getString("history", null) ?: return emptyList()
        val type = object : TypeToken<List<ScanHistoryItem>>() {}.type
        return gson.fromJson(json, type)
    }

    fun clearHistory() {
        prefs.edit().clear().apply()
    }
}
