package com.valance.medicine.domain.usecase

import androidx.datastore.core.DataStore
import com.valance.medicine.data.UserInfoOuterClass
import kotlinx.coroutines.flow.first

class GetUserInfoUseCase(private val dataStore: DataStore<UserInfoOuterClass.UserInfo>) {
    suspend fun execute(): UserInfoOuterClass.UserInfo {
        return dataStore.data.first()
    }
}
