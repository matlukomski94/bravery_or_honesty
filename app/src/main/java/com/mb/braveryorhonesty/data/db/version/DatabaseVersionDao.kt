package com.mb.braveryorhonesty.data.db.version

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DatabaseVersionDao {

    @Query("SELECT * FROM database_version LIMIT 1")
    fun getDatabaseVersion(): Flow<DatabaseVersionEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDatabaseVersion(databaseVersion: DatabaseVersionEntity)
}