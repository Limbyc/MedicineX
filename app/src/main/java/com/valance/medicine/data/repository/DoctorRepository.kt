package com.valance.medicine.data.repository

import com.valance.medicine.data.source.FirestoreDoctorDataSource
import com.valance.medicine.domain.mapper.toDomainModel
import com.valance.medicine.domain.model.Doctor

class DoctorRepository(private val dataSource: FirestoreDoctorDataSource) {

    suspend fun getAllDoctors(): List<Doctor> {
        val doctorDataModels = dataSource.getDoctors()
        return doctorDataModels.map { it.toDomainModel() }
    }
}
