package com.mb.braveryorhonesty.ui.play

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class PlayScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _categoryId = MutableStateFlow(-1)
    val categoryId: StateFlow<Int> = _categoryId

    init {
        val categoryIdFromArgs = savedStateHandle.get<Int>("categoryId") ?: -1
        _categoryId.value = categoryIdFromArgs
    }
}
