package com.mb.braveryorhonesty.ui.play

import androidx.lifecycle.SavedStateHandle
import com.mb.braveryorhonesty.base.BaseViewModel
import com.mb.braveryorhonesty.utils.OptionType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class PlayScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : BaseViewModel() {

    private val _categoryId = MutableStateFlow(-1)
    val categoryId: StateFlow<Int> = _categoryId

    private val braveryQuestions = listOf(
        "Zrób coś odważnego dla kategorii 1",
        "Zaśpiewaj piosenkę przed wszystkimi",
        "Zaproponuj komuś coś niestandardowego"
    )

    private val honestyQuestions = listOf(
        "Jakie jest Twoje największe marzenie?",
        "Czy kiedykolwiek złamałeś zasady?",
        "Co lubisz najbardziej w tej kategorii?"
    )

    private val _currentQuestion = MutableStateFlow("")
    val currentQuestion: StateFlow<String> = _currentQuestion

    init {
        val categoryIdFromArgs = savedStateHandle["categoryId"] ?: -1
        _categoryId.value = categoryIdFromArgs
    }

    fun getRandomQuestion(option: OptionType) {
        val questions = when (option) {
            OptionType.BRAVERY -> braveryQuestions
            OptionType.HONESTY -> honestyQuestions
        }
        _currentQuestion.update { questions.randomOrNull() ?: "No questions for this category" }
    }
}