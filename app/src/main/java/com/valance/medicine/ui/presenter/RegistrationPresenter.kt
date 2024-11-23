package com.valance.medicine.ui.presenter

import android.content.Context
import androidx.navigation.NavController
import com.valance.medicine.R
import com.valance.medicine.domain.usecase.SaveUserInfoUseCase
import com.valance.medicine.ui.model.UserModel
import com.valance.medicine.data.userInfoDataStore
import com.valance.medicine.ui.view.AuthContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RegistrationPresenter(
    private val userModel: UserModel,
    private val navController: NavController,
    private val context: Context,
    private val userAuthView: AuthContract.View
) {
    private val saveUserInfoUseCase = SaveUserInfoUseCase(context.userInfoDataStore)

    suspend fun registerUser(phone: String, password: String) {
        val userExists = userModel.checkIfUserExists(phone)
        if (userExists) {
            userAuthView.showUserAuth()
            return
        }

        val result = userModel.saveUser(phone, password)
        if (result.success) {
            val userId = result.userId
            val userPhone = result.phone

            if (userId != null) {
                if (userPhone != null) {
                    saveUserInfo(userId, userPhone)
                }
            }

            withContext(Dispatchers.Main) {
                navController.navigate(R.id.mainFragment)
            }
        }
    }

    private suspend fun saveUserInfo(userId: String, userPhone: String) {
        saveUserInfoUseCase.execute(userId, userPhone)
    }
}
