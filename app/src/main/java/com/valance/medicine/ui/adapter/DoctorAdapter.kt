package com.valance.medicine.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.valance.medicine.databinding.ItemDoctorBinding
import com.valance.medicine.ui.DoctorDiffCallback
import com.valance.medicine.ui.model.DoctorDisplayModel

class DoctorAdapter : ListAdapter<DoctorDisplayModel, DoctorAdapter.DoctorViewHolder>(
    DoctorDiffCallback
) {

    inner class DoctorViewHolder(private val binding: ItemDoctorBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(doctor: DoctorDisplayModel) {
            binding.name.text = doctor.name
            binding.profession.text = doctor.profession
            binding.addInfo.text = doctor.addInfo
            binding.compasitive.text = doctor.compasitive
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemDoctorBinding.inflate(inflater, parent, false)
        return DoctorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DoctorViewHolder, position: Int) {
        val doctor = getItem(position)
        holder.bind(doctor)
    }
}
