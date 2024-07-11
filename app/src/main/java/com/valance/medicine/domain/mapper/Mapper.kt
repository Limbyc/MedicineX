package com.valance.medicine.domain.mapper

import com.valance.medicine.data.model.DoctorDataModel
import com.valance.medicine.domain.model.Doctor
import com.valance.medicine.ui.model.DoctorDisplayModel

fun DoctorDataModel.toDomainModel(): Doctor {
    return Doctor(
        id = this.id,
        name = this.name,
        profession = this.profession,
        addInfo = this.addInfo,
        compasitive = this.compasitive,
    )
}

fun Doctor.toDisplayModel(): DoctorDisplayModel {
    return DoctorDisplayModel(
        id = "Doctor ID: ${this.id}",
        name = this.name,
        profession = this.profession,
        addInfo = this.addInfo,
        compasitive = this.compasitive,
    )
}