package com.sigarda.jurnalkas.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sigarda.jurnalkas.data.local.dao.TransactionDao
import com.sigarda.jurnalkas.data.local.dao.UserDao
import com.sigarda.jurnalkas.data.local.entity.TransactionEntity
import com.sigarda.jurnalkas.data.local.entity.UserEntity
import com.sigarda.jurnalkas.wrapper.Converters

@Database(
    entities = [TransactionEntity::class],
    version = 3,
    exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao

}