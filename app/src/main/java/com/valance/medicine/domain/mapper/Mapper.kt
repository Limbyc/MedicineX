package com.valance.medicine.domain.mapper

import android.content.Context
import com.valance.medicine.R
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

fun Doctor.toDisplayModel(context: Context): DoctorDisplayModel {
    return DoctorDisplayModel(
        id = context.getString(R.string.doctor_id_label, this.id.toString()),
        name = this.name,
        profession = this.profession,
        addInfo = this.addInfo,
        compasitive = this.compasitive,
    )
}