package com.valance.medicine.data.source

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import com.valance.medicine.data.model.DoctorDataModel

class FirestoreDoctorDataSource {
    private val db = FirebaseFirestore.getInstance()
    private val doctorsCollection = db.collection("Doctor")

    suspend fun getDoctors(): List<DoctorDataModel> = withContext(Dispatchers.IO) {
        try {
            val querySnapshot = doctorsCollection.get().await()
            val doctorsList = mutableListOf<DoctorDataModel>()
            for (document in querySnapshot.documents) {
                val doctor = document.toObject(DoctorDataModel::class.java)
                doctor?.let { doctorsList.add(it) }
            }
            doctorsList
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}
