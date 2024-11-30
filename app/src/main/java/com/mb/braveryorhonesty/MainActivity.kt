package com.mb.braveryorhonesty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.mb.braveryorhonesty.navigation.Navigation
import com.mb.braveryorhonesty.ui.theme.BraveryOrHonestyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BraveryOrHonestyTheme {
                val navController = rememberNavController()
                Navigation(navController)
            }
        }
    }
}