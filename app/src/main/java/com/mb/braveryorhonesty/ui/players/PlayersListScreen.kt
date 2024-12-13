package com.mb.braveryorhonesty.ui.players

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.mb.braveryorhonesty.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayersListScreen(
    onNavigateBack: () -> Unit,
    onStartGame: () -> Unit,
    viewModel: PlayersListViewModel = hiltViewModel<PlayersListViewModel>(),
) {
    val players by viewModel.players.collectAsState()
    var showAddPlayerDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.back)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = stringResource(id = R.string.players_list_title),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(players) { player ->
                        PlayerCard(
                            playerName = player.name,
                            onDelete = { viewModel.removePlayer(player.name) }
                        )
                    }

                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.AddCircle,
                                contentDescription = stringResource(id = R.string.add_player_button),
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier
                                    .size(60.dp)
                                    .clickable { showAddPlayerDialog = true }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Przycisk "Rozpocznij grę"
                Button(
                    onClick = onStartGame,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    enabled = players.isNotEmpty(),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary)
                ) {
                    Text(
                        text = stringResource(id = R.string.start_game_button),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onTertiary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            if (showAddPlayerDialog) {
                AddPlayerDialog(
                    onDismiss = { showAddPlayerDialog = false },
                    onAddPlayer = { name ->
                        viewModel.addPlayer(name)
                        showAddPlayerDialog = false
                    }
                )
            }
        }
    }
}


@Composable
fun AddPlayerDialog(
    onDismiss: () -> Unit,
    onAddPlayer: (String) -> Unit,
) {
    var playerName by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = stringResource(id = R.string.add_player_title)) },
        text = {
            TextField(
                value = playerName,
                onValueChange = { playerName = it },
                label = { Text(stringResource(id = R.string.player_name_hint)) },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    if (playerName.isNotBlank()) {
                        onAddPlayer(playerName.trim())
                    }
                })
            )
        },
        confirmButton = {
            Button(
                onClick = {
                    if (playerName.isNotBlank()) {
                        onAddPlayer(playerName.trim())
                    }
                }
            ) {
                Text(text = stringResource(id = R.string.add))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(id = R.string.cancel))
            }
        }
    )
}

@Composable
fun PlayerCard(
    playerName: String,
    onDelete: () -> Unit,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp),
        tonalElevation = 4.dp,
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = playerName,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = stringResource(id = R.string.delete_player_button),
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}
