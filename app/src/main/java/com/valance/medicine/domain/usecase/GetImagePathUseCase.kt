package com.valance.medicine.domain.usecase

import android.content.Context
import com.valance.medicine.data.imagePathDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class GetImagePathUseCase(private val context: Context) {
    suspend fun execute(): String? {
        return runBlocking {
            context.imagePathDataStore.data.first().path
        }
    }
}
