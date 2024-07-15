package com.valance.medicine.domain.usecase

import androidx.datastore.core.DataStore
import com.valance.medicine.data.UserInfoOuterClass

class SaveUserInfoUseCase(private val dataStore: DataStore<UserInfoOuterClass.UserInfo>) {
    suspend fun execute(newUserId: String, newUserPhone: String) {
        dataStore.updateData { userInfo ->
            userInfo.toBuilder()
                .setId(newUserId)
                .setUserPhone(newUserPhone)
                .build()
        }
    }
}
