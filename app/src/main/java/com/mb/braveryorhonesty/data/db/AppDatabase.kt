package com.mb.braveryorhonesty.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mb.braveryorhonesty.data.db.version.DatabaseVersionDao
import com.mb.braveryorhonesty.data.db.version.DatabaseVersionEntity

@Database(entities = [DatabaseVersionEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun databaseVersionDao(): DatabaseVersionDao
}