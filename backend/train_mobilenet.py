import json
import tensorflow as tf
from tensorflow.keras.preprocessing.image import ImageDataGenerator
from tensorflow.keras.applications import MobileNetV2
from tensorflow.keras import layers, models
from tensorflow.keras.callbacks import ModelCheckpoint

# Paths
data_dir = "C:/Users/naiks/Project/CottonCare/cotton_dataset/cotton"

# Image preprocessing
img_size = (224, 224)
batch_size = 32

# Data generator
datagen = ImageDataGenerator(
    rescale=1.0 / 255.0,
    validation_split=0.2
)

train_gen = datagen.flow_from_directory(
    data_dir,
    target_size=img_size,
    batch_size=batch_size,
    class_mode='categorical',
    subset='training'
)

val_gen = datagen.flow_from_directory(
    data_dir,
    target_size=img_size,
    batch_size=batch_size,
    class_mode='categorical',
    subset='validation'
)

print("Training samples:", train_gen.samples)
print("Validation samples:", val_gen.samples)
print("Classes:", train_gen.class_indices)

# Save class indices for later use (label mapping)
with open('class_indices.json', 'w') as f:
    json.dump(train_gen.class_indices, f)

# Model setup
base_model = MobileNetV2(weights='imagenet', include_top=False, input_shape=(224, 224, 3))
base_model.trainable = False

model = models.Sequential([
    base_model,
    layers.GlobalAveragePooling2D(),
    layers.Dense(128, activation='relu'),
    layers.Dropout(0.3),
    layers.Dense(train_gen.num_classes, activation='softmax')
])

model.compile(optimizer='adam', loss='categorical_crossentropy', metrics=['accuracy'])

# Checkpoint callback
checkpoint = ModelCheckpoint('ckpt-best.h5', monitor='val_accuracy', save_best_only=True)


def main():
    print("Starting training...")
    model.fit(
        train_gen,
        validation_data=val_gen,
        epochs=10,
        callbacks=[checkpoint],
        verbose=1
    )

    # Save final model
    model.save("cotton_disease_mobilenet.h5")


if __name__ == '__main__':
    main()