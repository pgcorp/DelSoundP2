package com.example.delsound

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.delsound.ui.UserSessionViewModel
import com.example.delsound.ui.navigation.SoundInNavGraph
import com.example.delsound.ui.theme.DelSoundTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DelSoundTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ){
                    val sessionViewModel : UserSessionViewModel = viewModel()
                    val navController = rememberNavController()
                    SoundInNavGraph(
                        navController = navController,
                        sessionViewModel = sessionViewModel
                    )
                }
            }
        }
    }
}


