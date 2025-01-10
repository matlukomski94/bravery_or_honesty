package com.mb.braveryorhonesty.ui.players

import androidx.lifecycle.viewModelScope
import com.mb.core.base.BaseViewModel
import com.mb.braveryorhonesty.data.Player
import com.mb.braveryorhonesty.data.player.PlayerDataStore
import com.mb.core.di.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayersListViewModel @Inject constructor(
    private val playerDataStore: PlayerDataStore,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : BaseViewModel() {

    val players: StateFlow<List<Player>> = playerDataStore.players
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    fun addPlayer(name: String) {
        viewModelScope.launch(ioDispatcher) {
            playerDataStore.addPlayer(name)
        }
    }

    fun removePlayer(name: String) {
        viewModelScope.launch(ioDispatcher) {
            playerDataStore.removePlayer(name)
        }
    }

    fun clearPlayers() {
        viewModelScope.launch(ioDispatcher) {
            playerDataStore.clearPlayers()
        }
    }
}