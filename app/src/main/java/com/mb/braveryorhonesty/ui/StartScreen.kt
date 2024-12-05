package com.mb.braveryorhonesty.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mb.braveryorhonesty.R

@Composable
fun StartScreen(
    onNavigateToPlayersList: () -> Unit,
    onNavigateToSettings: () -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.labelMedium
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = { onNavigateToPlayersList() },
                modifier = Modifier.fillMaxWidth(0.6f)
            ) {
                Text(text = stringResource(R.string.play), fontSize = 18.sp)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { onNavigateToSettings() },
                modifier = Modifier.fillMaxWidth(0.6f)
            ) {
                Text(text = stringResource(R.string.settings), fontSize = 18.sp)
            }
        }
    }
}

