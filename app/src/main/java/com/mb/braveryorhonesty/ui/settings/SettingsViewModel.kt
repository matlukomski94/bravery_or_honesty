package com.mb.braveryorhonesty.ui.settings

import com.mb.braveryorhonesty.utils.Settings
import com.mb.braveryorhonesty.utils.SettingsDataStore
import com.mb.braveryorhonesty.utils.Theme
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mb.braveryorhonesty.utils.Language
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val dataStore: SettingsDataStore,
) : ViewModel() {

    val settings: StateFlow<Settings> = dataStore.settings
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            Settings(Language.ENGLISH, Theme.LIGHT, true)
        )

    fun updateLanguage(language: Language) {
        viewModelScope.launch {
            dataStore.updateLanguage(language)
        }
    }

    fun updateTheme(theme: Theme) {
        viewModelScope.launch {
            dataStore.updateTheme(theme)
        }
    }

    fun updateRandomQuestions(enabled: Boolean) {
        viewModelScope.launch {
            dataStore.updateRandomQuestions(enabled)
        }
    }
}
