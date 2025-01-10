package com.mb.braveryorhonesty

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.datastore.preferences.core.emptyPreferences
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.mb.braveryorhonesty.data.db.version.DatabaseCheckResult
import com.mb.braveryorhonesty.data.db.version.DatabaseVersionRepository
import com.mb.braveryorhonesty.navigation.Navigation
import com.mb.braveryorhonesty.ui.theme.BraveryOrHonestyTheme
import com.mb.braveryorhonesty.utils.SettingsDataStore
import com.mb.braveryorhonesty.utils.Theme
import com.mb.braveryorhonesty.utils.Utils
import com.mb.braveryorhonesty.utils.dataStore
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var databaseVersionRepository: DatabaseVersionRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            when (val result = databaseVersionRepository.checkAndUpdateDatabaseVersion()) {
                is DatabaseCheckResult.RequiresUpdate -> {
                    Log.i(Utils.LOG_TAG, "database requires update")
                    //start update
                }
                is DatabaseCheckResult.UpToDate -> {
                    //nothing
                    Log.i(Utils.LOG_TAG, "database is up to date")
                }
                is DatabaseCheckResult.Error -> {
                    Log.e(Utils.LOG_TAG, "Database version error - ${result.message}")
                }
            }
        }

        enableEdgeToEdge()
        setContent {
            val settingsFlow = this.dataStore.data
            val preferences by settingsFlow.collectAsState(initial = emptyPreferences())

            val theme =
                preferences[SettingsDataStore.THEME]?.let { Theme.valueOf(it) } ?: Theme.DARK
            val isDarkTheme = theme == Theme.DARK

            BraveryOrHonestyTheme(darkTheme = isDarkTheme) {
                val navController = rememberNavController()
                Navigation(navController)
            }
        }
    }
}