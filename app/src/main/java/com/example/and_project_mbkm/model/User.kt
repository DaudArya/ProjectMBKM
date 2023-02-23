package com.example.and_project_mbkm.model

import android.graphics.Bitmap
import com.example.and_project_mbkm.data.local.entity.UserEntity


data class User(
    var id: Int = 0,
    var username: String? = null,
    var name: String? = null,
    var email: String? = null,
    var password: String? = null,
    var address: String? = null,
    var dateOfBirth: String? = null,
    var profilePhoto: Bitmap? = null
)

fun User.toUserEntity(): UserEntity =
    UserEntity(id, username, name, email, password, address, dateOfBirth, profilePhoto)
