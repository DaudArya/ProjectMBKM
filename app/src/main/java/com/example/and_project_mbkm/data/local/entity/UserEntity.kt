package com.example.and_project_mbkm.data.local.entity

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.and_project_mbkm.model.User
import java.io.Serializable

@Entity(tableName = "users")
data class UserEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "username")
    var username: String? = null,

    @ColumnInfo(name = "name")
    var name: String? = null,

    @ColumnInfo(name = "email")
    var email: String? = null,

    @ColumnInfo(name = "password")
    var password: String? = null,

    @ColumnInfo(name = "address")
    var address: String? = null,

    @ColumnInfo(name = "date_of_birth")
    var dateOfBirth: String? = null,

    @ColumnInfo(name = "profile_photo")
    var profilePhoto: Bitmap? = null
) : Serializable

fun UserEntity.toUser(): User =
    User(id, username, name, email, password, address, dateOfBirth, profilePhoto)