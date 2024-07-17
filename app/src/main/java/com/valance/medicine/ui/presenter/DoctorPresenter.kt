package com.valance.medicine.ui.presenter

import android.util.Log
import androidx.fragment.app.Fragment
import com.valance.medicine.data.repository.DoctorRepository
import com.valance.medicine.domain.mapper.toDisplayModel
import com.valance.medicine.ui.view.DoctorContractInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class DoctorPresenter @Inject constructor(
    private val repository: DoctorRepository
) : DoctorContractInterface.Presenter{

    private var view: DoctorContractInterface.View? = null


    override fun getDoctors() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val result = repository.getAllDoctors()
                if (result.isSuccess) {
                    val doctors = result.getOrNull() ?: listOf()
                    val displayDoctors = doctors.map { it.toDisplayModel((view as Fragment).requireContext()) }
                    view?.showDoctors(displayDoctors)
                } else {
                    val exception = result.exceptionOrNull()
                    Log.d("DoctorPresenter", "Error fetching doctors: ${exception?.message}")
                }
            } catch (e: Exception) {
                Log.d("DoctorPresenter", "Error in coroutine: ${e.message}")
            }
        }
    }
}
