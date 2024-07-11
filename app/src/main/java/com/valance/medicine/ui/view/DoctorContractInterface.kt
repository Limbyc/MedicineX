package com.valance.medicine.ui.view

import com.valance.medicine.domain.model.Doctor
import com.valance.medicine.ui.model.DoctorDisplayModel

interface DoctorContractInterface {
    interface View {
        fun showDoctors(doctors: List<DoctorDisplayModel>)
    }

    interface Presenter {
        fun getDoctors()
    }
}