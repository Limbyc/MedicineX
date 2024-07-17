package com.valance.medicine.ui

import androidx.recyclerview.widget.DiffUtil
import com.valance.medicine.ui.model.DoctorDisplayModel

object DoctorDiffCallback : DiffUtil.ItemCallback<DoctorDisplayModel>() {
    override fun areItemsTheSame(oldItem: DoctorDisplayModel, newItem: DoctorDisplayModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DoctorDisplayModel, newItem: DoctorDisplayModel): Boolean {
        return oldItem == newItem
    }
}
