package com.example.delsound.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.delsound.ui.UserSessionViewModel
import com.example.delsound.ui.screens.LoginScreen
import com.example.delsound.ui.screens.MainScreen
import com.example.delsound.ui.screens.PlaylistDetailScreen
import com.example.delsound.ui.screens.RegisterScreen

@Composable
fun SoundInNavGraph(
    navController: NavHostController,
    sessionViewModel: UserSessionViewModel
){
    NavHost(
        navController = navController,
        startDestination = SoundInRoutes.LOGIN
    ) {
        composable(SoundInRoutes.LOGIN) {
            LoginScreen(
                onNavigationToRegister = { navController.navigate(SoundInRoutes.REGISTER)
                },
                onLoginSuccess = {
                    navController.navigate(SoundInRoutes.MAIN){
                        popUpTo(SoundInRoutes.LOGIN) { inclusive = true }
                    }
                }
            )
        }
        composable(SoundInRoutes.REGISTER) {
            RegisterScreen(
                onNavigateToLogin = {
                    navController.navigate(SoundInRoutes.LOGIN){
                        popUpTo(SoundInRoutes.LOGIN) { inclusive = true }
                    }
                }
            )
        }
        composable(SoundInRoutes.MAIN) {
            MainScreen(
                sessionViewModel = sessionViewModel,
                onLogout = {
                    navController.navigate(SoundInRoutes.LOGIN) {
                        popUpTo(SoundInRoutes.MAIN) { inclusive = true }
                    }
                }
            )
        }

        composable(SoundInRoutes.PLAYLIST_DETAIL,
            arguments = listOf(
                navArgument("playlistId") {type = NavType.IntType})
        ) {
            backStackEntry ->
            val playlistId = backStackEntry.arguments?.getInt("playlistId") ?: 0
            // Mostrar el detalle de la playlist
            PlaylistDetailScreen(playlistId = playlistId,
                onNavigateBack = { navController.popBackStack() }
            )
        }

    }
}


