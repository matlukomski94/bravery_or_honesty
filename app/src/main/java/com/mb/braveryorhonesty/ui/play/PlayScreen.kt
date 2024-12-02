package com.mb.braveryorhonesty.ui.play

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mb.braveryorhonesty.R
import com.mb.braveryorhonesty.utils.OptionType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayScreen(
    onNavigateBack: () -> Unit,
    viewModel: PlayScreenViewModel = hiltViewModel(),
) {
    val categoryId by viewModel.categoryId.collectAsState()
    val currentQuestion by viewModel.currentQuestion.collectAsState()

    var selectedOption by remember { mutableStateOf<OptionType?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.app_name)) },
                navigationIcon = {
                    IconButton(onClick = { onNavigateBack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                if (selectedOption == null) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            text = "${stringResource(R.string.category)} $categoryId",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(16.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = {
                                selectedOption = OptionType.BRAVERY
                                viewModel.getRandomQuestion(OptionType.BRAVERY)
                            },
                            modifier = Modifier.fillMaxWidth(0.6f)
                        ) {
                            Text(text = OptionType.BRAVERY.displayName)
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = {
                                selectedOption = OptionType.HONESTY
                                viewModel.getRandomQuestion(OptionType.HONESTY)
                            },
                            modifier = Modifier.fillMaxWidth(0.6f)
                        ) {
                            Text(text = OptionType.HONESTY.displayName)
                        }
                    }
                } else {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "${selectedOption?.displayName}: $currentQuestion",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(16.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { selectedOption = null },
                            modifier = Modifier.fillMaxWidth(0.6f)
                        ) {
                            Text(text = stringResource(R.string.next))
                        }
                    }
                }
            }
        }
    }
}