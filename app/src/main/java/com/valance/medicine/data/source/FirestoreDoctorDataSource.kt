package com.valance.medicine.data.source

import com.google.firebase.firestore.FirebaseFirestore
import com.valance.medicine.data.model.DoctorDataModel
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirestoreDoctorDataSource @Inject constructor(private val firestore: FirebaseFirestore) {

    suspend fun getDoctors(): Result<List<DoctorDataModel>> {
        return try {
            val snapshot = firestore.collection("doctors").get().await()
            val doctors = snapshot.documents.map { document ->
                document.toObject(DoctorDataModel::class.java) ?: throw Exception("Invalid data")
            }
            Result.success(doctors)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
