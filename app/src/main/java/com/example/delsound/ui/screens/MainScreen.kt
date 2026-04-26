package com.example.delsound.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LibraryMusic
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.delsound.ui.LibraryViewModel
import com.example.delsound.ui.UserSessionViewModel
import com.example.delsound.ui.components.MiniPlayer
import com.example.delsound.ui.models.Song
import com.example.delsound.ui.navigation.SoundInRoutes
import com.example.delsound.ui.theme.DelSoundTheme

@Composable
fun MainScreen(
    sessionViewModel: UserSessionViewModel,
    onLogout: () -> Unit,
    libraryViewModel: LibraryViewModel = viewModel()
) {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route
    // Estado para la canción actual en reproducción y la lista de canciones
    var currentPlayingSong by remember { mutableStateOf<Song?>(null) }

    // Inicia Scaffold con el navController
    Scaffold(
        bottomBar = {
            Column {
                AnimatedVisibility(
                    visible = currentPlayingSong != null,
                    enter = slideInVertically { it } + fadeIn(),
                    exit = slideOutVertically { it } + fadeOut()
                ) {
                    currentPlayingSong?.let { song ->
                        MiniPlayer(song = song, onClose = { currentPlayingSong = null })
                    }
                }

                NavigationBar {
                    val items = listOf(
                        Triple(SoundInRoutes.LIBRARY, Icons.Default.LibraryMusic, "Library"),
                        Triple(SoundInRoutes.SEARCH, Icons.Default.Search, "Search"),
                        Triple(SoundInRoutes.PROFILE, Icons.Default.Person, "Profile")
                    ) // fin de la lista de items
                    items.forEach { (route, icon, label) ->
                        NavigationBarItem(
                            selected = currentRoute == route,
                            onClick = {
                                navController.navigate(route) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = { Icon(icon, contentDescription = label) },
                            label = { Text(label) }
                        )
                    } // for each item fin
                } // fin de la NavigationBar
            }// fin de la lambda de bottomBar
        } // fin del Column
    ) // fin de la lambda de Scaffold
    {
        // cuidado con la importacion debe ser: import androidx.navigation.compose.NavHost
            paddingValues ->
        NavHost(
            navController = navController,
            startDestination = SoundInRoutes.LIBRARY,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(SoundInRoutes.LIBRARY) {
                LibraryScreen(
                    onPlaylistClick = { playlist ->
                        currentPlayingSong = libraryViewModel.songs
                            .filter { it.playlistId == playlist.id }
                            .randomOrNull()
                        navController.navigate("playlistDetail/${playlist.id}")
                    }
                )
            }
            composable(SoundInRoutes.SEARCH) {
                SearchScreen(
                    onPlaylistClick = { playlist ->
                        navController.navigate("playlistDetail/${playlist.id}")
                    }
                )
            }
            composable(SoundInRoutes.PROFILE) { ProfileScreen() }
            composable(SoundInRoutes.PLAYLIST_DETAIL) {}
        } // fin de la NavHost
    } // fin de la lambda de Scaffold
}

private val previewViewModel = UserSessionViewModel()

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    DelSoundTheme {
        MainScreen(
            sessionViewModel = previewViewModel,
            onLogout = {}
        )
    }
}