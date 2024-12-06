package com.mb.braveryorhonesty.ui.play

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mb.braveryorhonesty.data.Player
import com.mb.braveryorhonesty.data.PlayerDataStore
import com.mb.braveryorhonesty.utils.OptionType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class PlayScreenViewModel @Inject constructor(
    private val playerDataStore: PlayerDataStore,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _categoryId = MutableStateFlow(savedStateHandle.get<Int>("categoryId") ?: -1)
    val categoryId: StateFlow<Int> get() = _categoryId

    val players: StateFlow<List<Player>> = playerDataStore.players
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    private val _currentPlayer = MutableStateFlow<Player?>(null)
    val currentPlayer: StateFlow<Player?> get() = _currentPlayer

    private val _currentQuestion = MutableStateFlow<String?>(null)
    val currentQuestion: StateFlow<String?> get() = _currentQuestion

    private val _selectedOption = MutableStateFlow<OptionType?>(null)
    val selectedOption: StateFlow<OptionType?> get() = _selectedOption

    init {
        getNextPlayer()
    }

    fun getNextPlayer() {
        _currentPlayer.value = playerDataStore.getNextPlayer()
        _selectedOption.value = null
        _currentQuestion.value = null
    }

    fun selectOption(option: OptionType) {
        _selectedOption.value = option
        _currentQuestion.value = getRandomQuestion(option, _categoryId.value)
    }

    private fun getRandomQuestion(option: OptionType, categoryId: Int): String {
        val questions = when (option) {
            OptionType.BRAVERY -> braveryQuestions
            OptionType.HONESTY -> honestyQuestions
        }
        return questions.randomOrNull() ?: "No questions for this category"
    }

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
}