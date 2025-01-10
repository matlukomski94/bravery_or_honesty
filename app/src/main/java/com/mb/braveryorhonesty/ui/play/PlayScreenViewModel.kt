package com.mb.braveryorhonesty.ui.play

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.mb.braveryorhonesty.data.player.PlayerDataStore
import com.mb.braveryorhonesty.utils.OptionType
import com.mb.core.base.BaseViewModel
import com.mb.core.di.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayScreenViewModel @Inject constructor(
    private val playerDataStore: PlayerDataStore,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(PlayScreenUIState())
    val uiState = _uiState.asStateFlow()

    init {
        val categoryIdFromArgs = savedStateHandle["categoryId"] ?: -1
        _uiState.value = _uiState.value.copy(categoryId = categoryIdFromArgs)

        loadPlayers()
        getNextPlayer()
    }

    fun getNextPlayer() = viewModelScope.launch(ioDispatcher) {
        val nextPlayer = playerDataStore.getNextPlayer()
        _uiState.value = _uiState.value.copy(
            currentPlayer = nextPlayer,
            selectedOption = null,
            currentQuestion = null
        )
    }

    fun selectOption(option: OptionType) {
        val question = getRandomQuestion(option, _uiState.value.categoryId)
        _uiState.value = _uiState.value.copy(
            selectedOption = option,
            currentQuestion = question
        )
    }

    private fun loadPlayers() {
        viewModelScope.launch(ioDispatcher) {
            val players = playerDataStore.players.stateIn(
                viewModelScope,
                SharingStarted.Eagerly,
                emptyList()
            ).value
            _uiState.value = _uiState.value.copy(players = players)
        }
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