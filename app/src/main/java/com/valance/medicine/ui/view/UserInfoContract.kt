package com.valance.medicine.ui.view

interface UserInfoContract {
    interface View {
        suspend fun showSuccessMessage(message: String)
        suspend fun showErrorMessage(message: String)
    }

    interface Presenter
}
