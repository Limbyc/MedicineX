package com.valance.medicine.domain.usecase

import android.content.Context
import com.valance.medicine.data.ImagePathOuterClass.ImagePath
import com.valance.medicine.data.imagePathDataStore

class SaveImagePathUseCase(private val context: Context) {
    suspend fun execute(path: String) {
        val imagePath = ImagePath.newBuilder().setPath(path).build()
        context.imagePathDataStore.updateData { imagePath }
    }
}
