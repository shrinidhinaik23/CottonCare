# 🌿 CottonCare — AI-Based Cotton Disease Detection App

An **offline AI-powered Android application** that detects cotton leaf diseases using **TensorFlow Lite** and provides **instant diagnosis with remedies**, even without internet access.

---

## 🚀 Why this project?

Farmers often lack access to fast and reliable crop diagnosis tools.
CottonCare solves this by bringing **AI directly to mobile devices**, enabling **real-time, offline disease detection**.

---

## ✨ Features

* 📷 Upload or capture cotton leaf image
* 🤖 AI-based disease prediction (on-device)
* 📊 Confidence score with smart thresholds
* ⚠️ Unknown detection for out-of-scope images
* 💊 Remedy suggestions for each disease
* 📴 Works completely offline
* 🧠 ML Kit validation (detects non-crop images / faces)
* 📚 Built-in disease information library
* 🕒 Local scan history tracking

---

## 🧠 AI Model Details

* Model: **MobileNetV2 (Transfer Learning)**
* Input size: **224 × 224 × 3**
* Framework: **TensorFlow → TensorFlow Lite**
* Output:

  * Disease name
  * Confidence %
  * Remedy suggestion

### Supported Classes

* Fusarium Wilt
* Bacterial Blight
* Curl Virus
* Healthy

---

## ⚙️ How it works

```
User Image
   ↓
Validation (ML Kit)
   ↓
Preprocessing (Resize + Normalize)
   ↓
TFLite Model Inference
   ↓
Confidence Threshold Check
   ↓
Result + Remedy + History Storage
```

---

## 📱 App Screenshots

> Add your screenshots here (VERY IMPORTANT)

```
assets/home.png
assets/prediction.png
assets/result.png
assets/library.png
```

---

## 📊 Model Performance (Add if possible)

* Training Accuracy: XX%
* Validation Accuracy: XX%
* Test Accuracy: XX%

> (Even approximate values are fine — but must be truthful)

---

## 🛠️ Tech Stack

### Mobile App

* Kotlin
* Jetpack Compose
* CameraX
* ML Kit

### Machine Learning

* TensorFlow / Keras
* MobileNetV2
* TensorFlow Lite

---

## ⚡ Installation

```bash
git clone https://github.com/shrinidhinaik23/CottonCare.git
cd CottonCare
```

Open in **Android Studio** and run the app.

---

## ⚠️ Limitations

* Limited dataset size
* Accuracy depends on image quality
* Model may not detect unseen diseases

---

## 🚀 Future Improvements

* Add more disease classes
* Improve dataset size
* Real-time camera detection
* Multilingual support for farmers
* Cloud sync (optional)

---

## 👨‍💻 Author

**Shrinidhi Naik**

---
