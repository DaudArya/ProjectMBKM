package com.example.and_project_mbkm.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.and_project_mbkm.data.local.dao.UserDao
import com.example.and_project_mbkm.data.local.entity.UserEntity
import com.example.and_project_mbkm.wrapper.Converters

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

}