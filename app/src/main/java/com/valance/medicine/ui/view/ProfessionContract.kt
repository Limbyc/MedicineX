package com.valance.medicine.ui.view


interface ProfessionContract {
    interface View {
        fun showData(data: List<Any?>)
    }

    interface Presenter
}