package com.mb.braveryorhonesty.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import kotlinx.coroutines.flow.Flow
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

    val players: Flow<List<String>> = context.playerDataStore.data.map { playerList ->
        playerList.playersList.map { it.name }
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
    }
}