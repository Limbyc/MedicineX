package com.valance.medicine.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import java.io.InputStream
import java.io.OutputStream

object ImagePathSerializer  : androidx.datastore.core.Serializer<ImagePathOuterClass.ImagePath> {
    override val defaultValue: ImagePathOuterClass.ImagePath
        get() = ImagePathOuterClass.ImagePath.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): ImagePathOuterClass.ImagePath {
        return ImagePathOuterClass.ImagePath.parseFrom(input)
    }

    override suspend fun writeTo(t: ImagePathOuterClass.ImagePath, output: OutputStream) {
        t.writeTo(output)
    }
}

val Context.imagePathDataStore: DataStore<ImagePathOuterClass.ImagePath> by dataStore(
    fileName = "image_path.proto",
    serializer = ImagePathSerializer
)
