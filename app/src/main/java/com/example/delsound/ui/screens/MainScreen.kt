package com.example.delsound.ui.screens

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.delsound.ui.UserSessionViewModel
import com.example.delsound.ui.navigation.SoundInRoutes
import com.example.delsound.ui.theme.DelSoundTheme

@Composable
fun MainScreen(
    sessionViewModel: UserSessionViewModel, onLogout:() -> Unit
){
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    // Inicia Scaffold con el navController
    Scaffold(
        bottomBar = {
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
                            navController.navigate(route){
                                popUpTo(navController.graph.startDestinationId){
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(icon, contentDescription = label) },
                        label = { Text(label) }
                    )

                }

            } // fin de la NavigationBar
        }// fin de la lambda de bottomBar
    ) // fin de la lambda de Scaffold
    {
        // cuidado con la importacion debe ser: import androidx.navigation.compose.NavHost
        paddingValues ->
        NavHost(
            navController = navController,
            startDestination = SoundInRoutes.LIBRARY,
            modifier = Modifier.padding(paddingValues)
        ){
            composable(SoundInRoutes.LIBRARY) {
                LibraryScreen(
                    onPlaylistClick = { playlist ->
                        navController.navigate("playlistDetail/${playlist.id}")
                    }
                )
            }
            composable(SoundInRoutes.SEARCH) { SearchScreen() }
            composable(SoundInRoutes.PROFILE) { ProfileScreen() }
            composable(SoundInRoutes.PLAYLIST_DETAIL ) {}
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