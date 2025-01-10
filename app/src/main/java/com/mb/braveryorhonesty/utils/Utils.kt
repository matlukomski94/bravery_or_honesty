package com.mb.braveryorhonesty.utils

import android.app.Activity
import android.app.LocaleManager
import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import android.os.LocaleList
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat

object Utils {

    const val LOG_TAG = "brave_or_honesty"

    private fun Context.findActivity(): Activity? = when (this) {
        is Activity -> this
        is ContextWrapper -> baseContext.findActivity()
        else -> null
    }

    fun setLanguage(context: Context, language: Language) {
        context.findActivity()?.runOnUiThread {
            val languageCode = when (language) {
                Language.ENGLISH -> "en"
                Language.POLISH -> "pl"
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                context.getSystemService(LocaleManager::class.java)
                    .applicationLocales = LocaleList.forLanguageTags(languageCode)
            } else {
                AppCompatDelegate.setApplicationLocales(
                    LocaleListCompat.forLanguageTags(
                        languageCode
                    )
                )
            }
        }
    }
}