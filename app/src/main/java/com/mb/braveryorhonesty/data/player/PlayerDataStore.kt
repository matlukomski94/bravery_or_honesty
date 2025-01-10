package com.mb.braveryorhonesty.data.player

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.mb.braveryorhonesty.data.Player
import com.mb.braveryorhonesty.data.PlayersList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.InputStream
import java.io.OutputStream

object PlayerListSerializer : Serializer<PlayersList> {
    override val defaultValue: PlayersList = PlayersList.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): PlayersList {
        return PlayersList.parseFrom(input)
    }

    override suspend fun writeTo(t: PlayersList, output: OutputStream) {
        t.writeTo(output)
    }
}

private val Context.playerDataStore: DataStore<PlayersList> by dataStore(
    fileName = "player_list.pb",
    serializer = PlayerListSerializer
)

class PlayerDataStore(private val context: Context) {

    private var currentIndex = -1

    val players: Flow<List<Player>> = context.playerDataStore.data.map { playerList ->
        playerList.playersList
    }

    suspend fun addPlayer(name: String) {
        context.playerDataStore.updateData { currentList ->
            val updatedList = currentList.toBuilder()
            updatedList.addPlayers(Player.newBuilder().setName(name).build())
            updatedList.build()
        }
    }

    suspend fun removePlayer(name: String) {
        context.playerDataStore.updateData { currentList ->
            val updatedList = currentList.toBuilder()
            updatedList.clearPlayers()
            updatedList.addAllPlayers(
                currentList.playersList.filter { it.name != name }
            )
            updatedList.build()
        }
    }

    suspend fun clearPlayers() {
        context.playerDataStore.updateData {
            it.toBuilder().clearPlayers().build()
        }
        resetIndex()
    }

    suspend fun getNextPlayer(): Player? {
        val currentPlayers = players.first()
        if (currentPlayers.isEmpty()) return null

        currentIndex = (currentIndex + 1) % currentPlayers.size
        return currentPlayers[currentIndex]
    }

    private fun resetIndex() {
        currentIndex = -1
    }
}