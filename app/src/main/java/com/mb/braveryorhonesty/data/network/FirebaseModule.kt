package com.mb.braveryorhonesty.data.network

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.mb.braveryorhonesty.utils.Utils
import kotlinx.coroutines.tasks.await

class FirebaseModule {

    private val firestore = FirebaseFirestore.getInstance()

    suspend fun fetchDatabaseVersion(): Int {
        Log.d(Utils.LOG_TAG, "Fetch database version")
        return try {
            firestore.collection("versions")
                .document("databaseVersion")
                .get()
                .await()
                .getLong("version")
                ?.toInt() ?: 0
        } catch (e: Exception) {
            0
        }
    }
}