package com.mb.braveryorhonesty.ui.settings

import androidx.compose.foundation.clickable
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
import com.mb.braveryorhonesty.utils.Language
import com.mb.braveryorhonesty.utils.Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onNavigateBack: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel(),
) {
    val settings by viewModel.settings.collectAsState()
    var showLanguageModal by remember { mutableStateOf(false) }
    var showThemeModal by remember { mutableStateOf(false) }

    Scaffold(topBar = {
        TopAppBar(title = { Text(text = stringResource(R.string.settings)) },
            navigationIcon = {
                IconButton(onClick = { onNavigateBack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            })
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = stringResource(R.string.game_options),
                style = MaterialTheme.typography.titleLarge
            )

            SettingItem(title = stringResource(R.string.language),
                value = settings.language.displayName,
                onClick = { showLanguageModal = true })
            SettingItem(title = stringResource(R.string.theme),
                value = settings.theme.displayName,
                onClick = { showThemeModal = true })
            SettingItem(title = stringResource(R.string.random_questions),
                value = if (settings.randomQuestions) stringResource(R.string.enabled) else stringResource(
                    R.string.disabled
                ),
                onClick = { viewModel.updateRandomQuestions(!settings.randomQuestions) })
        }
    }

    if (showLanguageModal) {
        SelectionModal(title = stringResource(R.string.select_language),
            options = Language.entries.map { it.displayName },
            onDismiss = { showLanguageModal = false },
            onSelect = { selected ->
                Language.fromDisplayName(selected)?.let {
                    viewModel.updateLanguage(it)
                }
                showLanguageModal = false
            })
    }

    if (showThemeModal) {
        SelectionModal(title = stringResource(R.string.select_theme),
            options = Theme.entries.map { it.displayName },
            onDismiss = { showThemeModal = false },
            onSelect = { selected ->
                Theme.fromDisplayName(selected)?.let {
                    viewModel.updateTheme(it)
                }
                showThemeModal = false
            })
    }
}

@Composable
fun SettingItem(title: String, value: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title, style = MaterialTheme.typography.bodyLarge)
        Text(text = value, style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun SelectionModal(
    title: String,
    options: List<String>,
    onDismiss: () -> Unit,
    onSelect: (String) -> Unit,
) {
    AlertDialog(onDismissRequest = onDismiss, title = { Text(text = title) }, text = {
        Column {
            options.forEach { option ->
                Text(text = option,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable { onSelect(option) })
            }
        }
    }, confirmButton = {
        TextButton(onClick = onDismiss) {
            Text(text = stringResource(R.string.cancel))
        }
    })
}
