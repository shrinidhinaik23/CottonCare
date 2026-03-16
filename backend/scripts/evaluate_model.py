import os
import json
import numpy as np
from sklearn.metrics import classification_report, confusion_matrix
from tensorflow.keras.models import load_model
from tensorflow.keras.preprocessing import image
from PIL import Image

# Paths based on your setup
MODEL_PATH = "model/cotton_disease_mobilenet.h5"
CLASS_JSON = "model/class_indices.json"
TEST_DIR = "cotton_dataset/cotton"

# Load model
print("🔹 Loading model...")
model = load_model(MODEL_PATH)
print("✅ Model loaded!")

# Load class labels
with open(CLASS_JSON, 'r') as f:
    class_indices = json.load(f)
class_names = [None] * len(class_indices)
for name, idx in class_indices.items():
    class_names[idx] = name

# Prepare test data
y_true = []
y_pred = []

print("🔍 Evaluating test images...")
for class_name in os.listdir(TEST_DIR):
    class_path = os.path.join(TEST_DIR, class_name)
    if not os.path.isdir(class_path):
        continue

    for img_name in os.listdir(class_path):
        img_path = os.path.join(class_path, img_name)
        try:
            img = Image.open(img_path).convert("RGB").resize((224, 224))
            img_array = np.array(img) / 255.0
            img_array = np.expand_dims(img_array, axis=0)

            preds = model.predict(img_array)[0]
            pred_idx = np.argmax(preds)
            pred_class = class_names[pred_idx]

            y_true.append(class_name)
            y_pred.append(pred_class)

        except Exception as e:
            print(f"⚠️ Error with image {img_name}: {e}")

# Results
print("\n📊 Classification Report:")
print(classification_report(y_true, y_pred, target_names=class_names))

print("📉 Confusion Matrix:")
print(confusion_matrix(y_true, y_pred, labels=class_names))