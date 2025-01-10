package com.mb.braveryorhonesty.data.db.version

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "database_version")
data class DatabaseVersionEntity(
    @PrimaryKey val id: Int = 1,
    val version: Int
)