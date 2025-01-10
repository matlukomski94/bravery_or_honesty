package com.mb.braveryorhonesty.data.db.version

import com.mb.braveryorhonesty.data.network.FirebaseModule
import kotlinx.coroutines.flow.first
import javax.inject.Inject

sealed class DatabaseCheckResult {
    data object UpToDate : DatabaseCheckResult()
    data object RequiresUpdate : DatabaseCheckResult()
    data class Error(val message: String) : DatabaseCheckResult()
}

class DatabaseVersionRepository @Inject constructor(
    private val databaseVersionDao: DatabaseVersionDao,
    private val firebaseModule: FirebaseModule
) {

    suspend fun checkAndUpdateDatabaseVersion(): DatabaseCheckResult {
        return try {
            val localVersion = databaseVersionDao.getDatabaseVersion().first()?.version ?: 0
            val remoteVersion = firebaseModule.fetchDatabaseVersion()

            if (remoteVersion > localVersion) {
                databaseVersionDao.insertDatabaseVersion(DatabaseVersionEntity(version = remoteVersion))
                DatabaseCheckResult.RequiresUpdate
            } else {
                DatabaseCheckResult.UpToDate
            }
        } catch (e: Exception) {
            DatabaseCheckResult.Error(e.message ?: "Unknown error")
        }
    }
}