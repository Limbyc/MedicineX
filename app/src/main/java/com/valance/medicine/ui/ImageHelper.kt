package com.valance.medicine.ui

import android.content.Context
import android.content.ContextWrapper
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.valance.medicine.R
import com.valance.medicine.domain.usecase.GetImagePathUseCase
import com.valance.medicine.domain.usecase.SaveImagePathUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

class ImageHelper {

    fun saveImageToInternalStorage(context: Context, bitmap: Bitmap): String? {
        val contextWrapper = ContextWrapper(context)
        val directory = contextWrapper.getDir("images", Context.MODE_PRIVATE)
        val file = File(directory, "profile_image.jpg")

        var outputStream: FileOutputStream? = null
        try {
            outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        } finally {
            outputStream?.close()
        }
        return file.absolutePath
    }

    fun loadImageFromPath(context: Context, callback: (Bitmap) -> Unit) {
        val getImageUseCase = GetImagePathUseCase(context)
        CoroutineScope(Dispatchers.IO).launch {
            val imagePath = getImageUseCase.execute() ?: return@launch
            val bitmap = if (imagePath.isNotEmpty()) {
                BitmapFactory.decodeFile(imagePath)
            } else {
                BitmapFactory.decodeResource(context.resources, R.drawable.default_image)
            }
            withContext(Dispatchers.Main) {
                callback(bitmap)
            }
        }
    }

    fun saveImagePathToProto(context: Context, path: String?, onComplete: (Boolean) -> Unit) {
        val saveUseCase = SaveImagePathUseCase(context)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                saveUseCase.execute(path ?: "")
                withContext(Dispatchers.Main) {
                    onComplete(true)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    onComplete(false)
                }
            }
        }
    }

}
