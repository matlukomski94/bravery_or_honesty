package com.mb.braveryorhonesty.ui.category

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CategoryViewModel : ViewModel() {
    private val _categories = MutableStateFlow(
        listOf(
            "All" to -1,
            "Friends" to 1,
            "Family" to 2,
            "18+" to 3,
            "Party" to 4
        )
    )
    val categories: StateFlow<List<Pair<String, Int>>> = _categories
}