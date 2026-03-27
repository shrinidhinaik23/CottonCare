COTTONCARE

CottonCare is an offline AI-powered Android application for cotton leaf disease detection and crop support. It helps farmers or agriculture students capture or upload a cotton leaf image, classify common diseases using a TensorFlow Lite model, and view remedies and disease details directly inside the app.

--------------------------------------------------

FEATURES

- Offline cotton leaf disease detection using TensorFlow Lite
- Capture image using camera or choose image from gallery
- Cotton leaf validation before prediction
- Confidence-based prediction with unknown fallback
- Disease detail screen with symptoms, causes, treatment, and prevention
- Built-in disease library
- Scan history stored locally on device
- Splash screen and onboarding flow
- Jetpack Compose UI with navigation

--------------------------------------------------

SUPPORTED CLASSES

- Bacterial Blight
- Cotton Leaf Curl Virus
- Fusarium Wilt
- Healthy Plant

--------------------------------------------------

TECH STACK

ANDROID APP
- Kotlin
- Jetpack Compose
- Navigation Compose
- CameraX
- TensorFlow Lite
- Gson
- Coil
- ML Kit

ML BACKEND
- Python
- TensorFlow / Keras
- MobileNetV2
- NumPy
- Pillow
- scikit-learn

--------------------------------------------------

HOW IT WORKS

1. User opens the app
2. Splash screen is shown
3. User completes onboarding or skips
4. User captures or uploads an image
5. Image is resized to 224 x 224
6. TensorFlow Lite model predicts disease
7. If confidence is low → result is UNKNOWN
8. Otherwise, app displays:
   - Disease name
   - Severity
   - Remedy
   - Confidence
9. Scan history is stored locally

--------------------------------------------------

PROJECT STRUCTURE

CottonCare/
│
├── app/
│   ├── src/main/
│   │   ├── assets/
│   │   │   ├── class_indices.json
│   │   │   └── cotton_disease_mobilenet.tflite
│   │   ├── java/com/example/shrinidhi/
│   │   │   ├── model/
│   │   │   ├── navigation/
│   │   │   ├── ui/
│   │   │   │   ├── components/
│   │   │   │   ├── screens/
│   │   │   │   └── theme/
│   │   │   ├── utils/
│   │   │   └── MainActivity.kt
│   │   ├── res/
│   │   └── AndroidManifest.xml
│   └── build.gradle.kts
│
├── backend/
│   ├── model/
│   ├── scripts/
│   ├── class_indices.json
│   ├── train_mobilenet.py
│   ├── evaluate_model.py
│   ├── test_model.py
│   ├── convert_to_tflite.py
│   └── requirements.txt
│
├── build.gradle.kts
├── settings.gradle.kts
└── gradle/

--------------------------------------------------

ANDROID SETUP

PREREQUISITES
- Android Studio
- Android SDK
- Minimum SDK 24+
- Kotlin + Compose enabled

STEPS

1. Clone the repo
   git clone https://github.com/shrinidhinaik23/CottonCare.git

2. Open in Android Studio

3. Let Gradle sync complete

4. Ensure assets exist:
   - cotton_disease_mobilenet.tflite
   - class_indices.json

5. Run on emulator or device

--------------------------------------------------

ML MODEL TRAINING

MODEL DETAILS
- MobileNetV2 (ImageNet weights)
- Input: 224 x 224 x 3
- Softmax output layer

TRAIN

cd backend
python train_mobilenet.py

OUTPUT
- cotton_disease_mobilenet.h5
- class_indices.json

EVALUATE

python evaluate_model.py

TEST

python test_model.py

CONVERT TO TFLITE

python convert_to_tflite.py

--------------------------------------------------

PREDICTION LOGIC

- Image resized to 224 x 224
- Normalized to [0,1]
- TFLite inference runs on device
- Highest probability selected
- If confidence < 70% → UNKNOWN

--------------------------------------------------

MAIN SCREENS

- Splash Screen
- Welcome Screen
- Detect Screen
- Home Screen
- Scan Screen
- Prediction Screen
- Disease Library Screen
- Disease Detail Screen

--------------------------------------------------

LIMITATIONS

- Only 4 disease classes supported
- Backend paths are machine-specific
- Android-only app
- No cloud sync
- Unknown detection is rule-based

--------------------------------------------------

FUTURE IMPROVEMENTS

- Add more diseases
- Improve unknown detection
- Add multilingual support
- Weather integration
- Cloud backup
- Better dataset and accuracy
- Strong image validation

--------------------------------------------------

USE CASE

- Farmers for quick diagnosis
- Students for AI projects
- Offline rural usage
- Android + ML integration demo

--------------------------------------------------

AUTHOR

Shrinidhi Naik
GitHub: https://github.com/shrinidhinaik23

--------------------------------------------------

LICENSE

This project is for educational and academic purposes.
