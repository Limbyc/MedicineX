package com.valance.medicine.data.repository

import com.valance.medicine.data.source.FirestoreDoctorDataSource
import com.valance.medicine.domain.mapper.toDomainModel
import com.valance.medicine.domain.model.Doctor
import javax.inject.Inject

class DoctorRepository @Inject constructor(private val dataSource: FirestoreDoctorDataSource) {

    suspend fun getAllDoctors(): Result<List<Doctor>> {
        return dataSource.getDoctors().map { doctorDataModels ->
            doctorDataModels.map { it.toDomainModel() }
        }
    }
}
