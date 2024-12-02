package com.mb.braveryorhonesty.utils

enum class OptionType(val displayName: String) {
    BRAVERY("Bravery"),
    HONESTY("Truth");

    companion object {
        fun fromDisplayName(name: String): OptionType? {
            return entries.find { it.displayName == name }
        }
    }
}