package com.valance.medicine.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import java.io.InputStream
import java.io.OutputStream

object UserInfoSerializer : androidx.datastore.core.Serializer<UserInfoOuterClass.UserInfo> {
    override val defaultValue: UserInfoOuterClass.UserInfo
        get() = UserInfoOuterClass.UserInfo.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): UserInfoOuterClass.UserInfo {
        return UserInfoOuterClass.UserInfo.parseFrom(input)
    }

    override suspend fun writeTo(t: UserInfoOuterClass.UserInfo, output: OutputStream) {
        t.writeTo(output)
    }
}

val Context.userInfoDataStore: DataStore<UserInfoOuterClass.UserInfo> by dataStore(
    fileName = "user_info.proto",
    serializer = UserInfoSerializer
)
