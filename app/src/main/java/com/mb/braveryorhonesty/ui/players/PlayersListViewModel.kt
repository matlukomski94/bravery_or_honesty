package com.mb.braveryorhonesty.ui.players

import androidx.lifecycle.viewModelScope
import com.mb.braveryorhonesty.base.BaseViewModel
import com.mb.braveryorhonesty.data.PlayerDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayersListViewModel @Inject constructor(
    private val playerDataStore: PlayerDataStore
) : BaseViewModel() {

    val players: StateFlow<List<String>> = playerDataStore.players
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    fun addPlayer(name: String) {
        viewModelScope.launch {
            playerDataStore.addPlayer(name)
        }
    }

    fun removePlayer(name: String) {
        viewModelScope.launch {
            playerDataStore.removePlayer(name)
        }
    }

    fun clearPlayers() {
        viewModelScope.launch {
            playerDataStore.clearPlayers()
        }
    }
}