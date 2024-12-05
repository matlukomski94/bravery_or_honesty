package com.mb.braveryorhonesty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.datastore.preferences.core.emptyPreferences
import androidx.navigation.compose.rememberNavController
import com.mb.braveryorhonesty.navigation.Navigation
import com.mb.braveryorhonesty.ui.theme.BraveryOrHonestyTheme
import com.mb.braveryorhonesty.utils.SettingsDataStore
import com.mb.braveryorhonesty.utils.Theme
import com.mb.braveryorhonesty.utils.dataStore
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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