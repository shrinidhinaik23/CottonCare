import os
import json
import numpy as np
from tensorflow.keras.models import load_model
from tensorflow.keras.preprocessing import image
from PIL import Image

# Paths (change if needed)
MODEL_PATH = "cotton_disease_mobilenet.h5"
CLASS_JSON = "class_indices.json"

# Load trained model
print("🔹 Loading model...")
if not os.path.exists(MODEL_PATH):
    raise FileNotFoundError("❌ Model not found! Please train the model first.")
model = load_model(MODEL_PATH)
print("✅ Model loaded successfully!")

# Load class labels
if os.path.exists(CLASS_JSON):
    with open(CLASS_JSON, 'r') as f:
        class_indices = json.load(f)
    class_names = [None] * len(class_indices)
    for name, idx in class_indices.items():
        class_names[idx] = name
    print("✅ Loaded class labels:", class_names)
else:
    raise FileNotFoundError("❌ class_indices.json not found! Run training first to generate it.")

# Function to make prediction
def predict_cotton_image(img_path):
    if not os.path.exists(img_path):
        raise FileNotFoundError(f"Image not found: {img_path}")

    print(f"\n🖼️ Predicting: {os.path.basename(img_path)}")

    img = Image.open(img_path).convert("RGB").resize((224, 224))
    img_array = np.array(img) / 255.0
    img_array = np.expand_dims(img_array, axis=0)

    preds = model.predict(img_array)[0]
    top_idx = np.argmax(preds)
    predicted_class = class_names[top_idx]
    confidence = preds[top_idx]

    print(f"🔸 Predicted Class: {predicted_class}")
    print(f"🔸 Confidence: {confidence:.4f}")

    print("\nTop 3 predictions:")
    top3 = preds.argsort()[-3:][::-1]
    for i in top3:
        print(f"  {class_names[i]}: {preds[i]:.4f}")

# Main function
if __name__ == "__main__":
    img_path = input("\nEnter full path of a cotton leaf image: ").strip()
    predict_cotton_image(img_path)
