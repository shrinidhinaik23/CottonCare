package com.example.shrinidhi.model

data class Disease(
    val name: String,
    val description: String,
    val severity: String, // "low", "medium", "high"
    val symptoms: List<String>,
    val causes: List<String>,
    val treatment: List<String>,
    val prevention: List<String>
)
