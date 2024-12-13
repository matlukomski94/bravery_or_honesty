package com.mb.braveryorhonesty.ui.settings

import androidx.lifecycle.viewModelScope
import com.mb.core.base.BaseViewModel
import com.mb.braveryorhonesty.utils.Language
import com.mb.braveryorhonesty.utils.Settings
import com.mb.braveryorhonesty.utils.SettingsDataStore
import com.mb.braveryorhonesty.utils.Theme
import com.mb.core.di.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val dataStore: SettingsDataStore,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : BaseViewModel() {

    val settings: StateFlow<Settings> = dataStore.settings
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            Settings(Language.ENGLISH, Theme.LIGHT, true)
        )

    fun updateLanguage(language: Language) {
        viewModelScope.launch(ioDispatcher) {
            dataStore.updateLanguage(language)
        }
    }

    fun updateTheme(theme: Theme) {
        viewModelScope.launch(ioDispatcher) {
            dataStore.updateTheme(theme)
        }
    }

    fun updateRandomQuestions(enabled: Boolean) {
        viewModelScope.launch(ioDispatcher) {
            dataStore.updateRandomQuestions(enabled)
        }
    }
}
