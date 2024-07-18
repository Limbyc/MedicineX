package com.valance.medicine.ui.presenter

import android.content.Context
import android.util.Log
import com.valance.medicine.ui.model.UserModel
import com.valance.medicine.ui.view.UserInfoContract
import javax.inject.Inject

class UserInfoPresenter(private val view: UserInfoContract.View, private val context: Context) {
    private val userModel = UserModel(context)

    /*
     TODO: сделай sealed class, в котором будут разные стейты (Success, Failed (типы ошибок)
      и внутри будет текст через @StringRes val message: Int)
     */
    suspend fun addUserInfo(phone: String, name: String, lastName: String, birthday: String) {
        try {
            val result = userModel.addUserInfo(phone, name, lastName, birthday)
            if (result.success) {
                view.showSuccessMessage("Информация пользователя успешно обновлена")
            } else {
                view.showErrorMessage("Ошибка при обновлении информации пользователя")
                Log.e("UserInfoPresenter", "Error updating user info: $result")
            }
        } catch (e: Exception) {
            view.showErrorMessage("Ошибка при обновлении информации пользователя")
            Log.e("UserInfoPresenter", "Error updating user info: ${e.message}", e)
        }
    }

}
