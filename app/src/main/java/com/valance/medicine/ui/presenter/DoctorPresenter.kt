package com.valance.medicine.ui.presenter

import androidx.fragment.app.Fragment
import com.valance.medicine.data.repository.DoctorRepository
import com.valance.medicine.domain.mapper.toDisplayModel
import com.valance.medicine.ui.view.DoctorContract
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class DoctorPresenter(
    private val repository: DoctorRepository,
) : DoctorContract.Presenter {

    private var view: DoctorContract.View? = null


    override fun getDoctors() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val result = repository.getAllDoctors()
                if (result.isSuccess) {
                    val doctors = result.getOrNull() ?: listOf()
                    val displayDoctors =
                        doctors.map { it.toDisplayModel((view as Fragment).requireContext()) }
                    view?.showDoctors(displayDoctors)
                } else {
                    val exception = result.exceptionOrNull()
                }
            } catch (e: Exception) {
                // todo: add error handling
            }
        }
    }
}
