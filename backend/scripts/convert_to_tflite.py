import tensorflow as tf

# Load your trained model
model = tf.keras.models.load_model("model/cotton_disease_mobilenet.h5")

# Convert to TensorFlow Lite
converter = tf.lite.TFLiteConverter.from_keras_model(model)
tflite_model = converter.convert()

# Save the converted model
with open("model/cotton_disease_mobilenet.tflite", "wb") as f:
    f.write(tflite_model)

print("✅ Model converted to TensorFlow Lite!")