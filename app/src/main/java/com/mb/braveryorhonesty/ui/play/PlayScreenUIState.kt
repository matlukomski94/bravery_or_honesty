package com.mb.braveryorhonesty.ui.play

import com.mb.braveryorhonesty.data.Player
import com.mb.braveryorhonesty.utils.OptionType

data class PlayScreenUIState(
    val categoryId: Int = -1,
    val players: List<Player> = emptyList(),
    val currentPlayer: Player? = null,
    val currentQuestion: String? = null,
    val selectedOption: OptionType? = null
)
