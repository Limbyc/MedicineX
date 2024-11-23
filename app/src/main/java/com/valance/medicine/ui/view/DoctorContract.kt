package com.valance.medicine.ui.view

import com.valance.medicine.ui.model.DoctorDisplayModel

interface DoctorContract {
    interface View {
        fun showDoctors(doctors: List<DoctorDisplayModel>)
    }

    interface Presenter {
        fun getDoctors()
    }
}