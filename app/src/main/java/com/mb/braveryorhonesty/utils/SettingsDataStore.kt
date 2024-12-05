package com.mb.braveryorhonesty.utils

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore("settings")

class SettingsDataStore(private val context: Context) {

    companion object {
        val LANGUAGE = stringPreferencesKey("language")
        val THEME = stringPreferencesKey("theme")
        val RANDOM_QUESTIONS = booleanPreferencesKey("random_questions")
    }

    val settings: Flow<Settings> = context.dataStore.data.map { preferences ->
        Settings(
            language = preferences[LANGUAGE]?.let { Language.valueOf(it) } ?: Language.ENGLISH,
            theme = preferences[THEME]?.let { Theme.valueOf(it) } ?: Theme.DARK,
            randomQuestions = preferences[RANDOM_QUESTIONS] ?: true
        )
    }

    suspend fun updateLanguage(language: Language) {
        context.dataStore.edit { preferences ->
            preferences[LANGUAGE] = language.name
        }
    }

    suspend fun updateTheme(theme: Theme) {
        context.dataStore.edit { preferences ->
            preferences[THEME] = theme.name
        }
    }

    suspend fun updateRandomQuestions(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[RANDOM_QUESTIONS] = enabled
        }
    }
}

data class Settings(
    val language: Language,
    val theme: Theme,
    val randomQuestions: Boolean
)

enum class Language(val displayName: String) {
    POLISH("Polski"),
    ENGLISH("English");

    companion object {
        fun fromDisplayName(name: String): Language? {
            return entries.find { it.displayName == name }
        }
    }
}

enum class Theme(val displayName: String) {
    LIGHT("Light"),
    DARK("Dark");

    companion object {
        fun fromDisplayName(name: String): Theme? {
            return entries.find { it.displayName == name }
        }
    }
}