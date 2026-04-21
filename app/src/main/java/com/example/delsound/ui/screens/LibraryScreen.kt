package com.example.delsound.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.delsound.ui.LibraryViewModel
import com.example.delsound.ui.components.PlaylistCard
import com.example.delsound.ui.models.Playlist
import com.example.delsound.ui.theme.DelSoundTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryScreen(viewModel: LibraryViewModel = viewModel(), onPlaylistClick: (Playlist) -> Unit){
    // Obtener la lista de playlists desde el ViewModel como un StateFlow de Compose
    val playlists by viewModel.playlists.collectAsStateWithLifecycle()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Library") },
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /* pendiente */ }) {
                Icon(Icons.Default.Add, "Add Playlist")
            }
        }
    ) {
        paddingValues ->
        if (playlists.isEmpty()) {
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
                contentAlignment = Alignment.Center)
            {
                Text("No playlists yet, click the button  + below to add one")
            }
        }
        else{
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 160.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(playlists, key = { it.id }) { playlist ->
                    PlaylistCard(
                        playlist = playlist,
                        onClick = { onPlaylistClick(playlist) },
                        onLongClick = { /* later */ }
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun LibraryScreenPreview() {
    DelSoundTheme {
        LibraryScreen(
            onPlaylistClick = {}
        )
    }
}