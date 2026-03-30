# 🌿 CottonCare — AI-Based Cotton Disease Detection App

An **offline AI-powered Android application** that detects cotton leaf diseases using **TensorFlow Lite** and provides **instant diagnosis with remedies**, even without internet access.

---

## 🚀 Why this project?

Farmers often lack access to quick and reliable crop diagnosis tools.
CottonCare solves this by bringing **AI directly to mobile devices**, enabling **real-time, offline disease detection**.

---

## ✨ Features

* 📷 Capture or upload cotton leaf images
* 🤖 On-device AI disease prediction (offline)
* 📊 Confidence score with threshold-based validation
* ⚠️ Unknown detection for non-cotton / unclear images
* 💊 Remedy suggestions for detected diseases
* 📴 Works completely offline (no API required)
* 🧠 ML Kit validation (prevents invalid inputs like faces)
* 📚 Built-in disease library with severity levels
* 🕒 Local scan history tracking

---

## 📱 App Screenshots

### 🏠 Home Screen

### 📷 Scan Screen

### 🤖 Prediction Result

### 📚 Disease Library

### 🚀 Onboarding Experience

---

## 🧠 AI Model Details

* Model: **MobileNetV2 (Transfer Learning)**
* Input Size: **224 × 224 × 3**
* Framework: **TensorFlow → TensorFlow Lite**
* Runs fully on-device (no internet required)

### Supported Classes

* Fusarium Wilt
* Bacterial Blight
* Cotton Leaf Curl Virus
* Healthy Plant

---

## ⚙️ How it works

```text
User Image
   ↓
ML Kit Validation (crop check)
   ↓
Preprocessing (Resize + Normalize)
   ↓
TensorFlow Lite Inference
   ↓
Confidence Threshold Check
   ↓
Prediction + Remedy Display
   ↓
Stored in Local History
```

---

## 📊 Example Prediction Output

* Disease: **Bacterial Blight**
* Severity: **High**
* Confidence: **99.51%**
* Remedy: Use copper-based fungicides, remove infected leaves, avoid overhead irrigation.

---

## 🛠️ Tech Stack

### 📱 Mobile App

* Kotlin
* Jetpack Compose
* CameraX
* ML Kit

### 🤖 Machine Learning

* TensorFlow / Keras
* MobileNetV2
* TensorFlow Lite

---

## ⚡ Installation

```bash
git clone https://github.com/shrinidhinaik23/CottonCare.git
cd CottonCare
```

Open the project in **Android Studio** and run the app.

---

## ⚠️ Limitations

* Limited dataset size
* Accuracy depends on image quality
* May not detect unseen diseases
* No cloud sync (offline-only design)

---

## 🚀 Future Improvements

* Expand dataset for better accuracy
* Add more disease classes
* Real-time camera detection
* Multilingual support for farmers
* Optional cloud-based analytics

---

## 👨‍💻 Author

**Shrinidhi Naik**

---

##
