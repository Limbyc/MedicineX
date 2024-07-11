package com.valance.medicine.ui.presenter

import android.util.Log
import com.valance.medicine.data.repository.DoctorRepository
import com.valance.medicine.domain.mapper.toDisplayModel
import com.valance.medicine.domain.model.Doctor
import com.valance.medicine.ui.model.DoctorDisplayModel
import com.valance.medicine.ui.view.DoctorContractInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DoctorPresenter(
    private val view: DoctorContractInterface.View,
    private val repository: DoctorRepository
) : DoctorContractInterface.Presenter {

    override fun getDoctors() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val doctors: List<Doctor> = repository.getAllDoctors()
                val displayDoctors = doctors.map { it.toDisplayModel() } // Применяем маппер к каждому доктору
                view.showDoctors(displayDoctors)
            } catch (e: Exception) {
                Log.d("", e.toString())
            }
        }
    }
}
