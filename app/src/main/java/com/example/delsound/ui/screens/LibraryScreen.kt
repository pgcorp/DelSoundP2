package com.example.delsound.ui.screens

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.delsound.ui.LibraryViewModel
import com.example.delsound.ui.components.PlaylistBottomSheet
import com.example.delsound.ui.components.PlaylistCard
import com.example.delsound.ui.models.Playlist
import com.example.delsound.ui.theme.DelSoundTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryScreen(viewModel: LibraryViewModel = viewModel(), onPlaylistClick: (Playlist) -> Unit){
    // Obtener la lista de playlists desde el ViewModel como un StateFlow de Compose
    val selectedTab by viewModel.selectedTab.collectAsStateWithLifecycle()
    val filteredPlaylist by viewModel.filteredPlaylist.collectAsStateWithLifecycle()
    // Estado para la playlist seleccionada en el bottom sheet
    var selectedPlaylist by remember { mutableStateOf<Playlist?>(null) }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            val playlists by viewModel.playlists.collectAsStateWithLifecycle()
            Column(
            ) {
                TopAppBar(
                    title = { Text(
                        text = "Library",
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxSize(),

                    ) },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
                TabRow(
                    selectedTabIndex = selectedTab,
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ) {
                    val tabs = listOf("All", "Favorites")
                    tabs.forEachIndexed { index, tile ->
                        Tab(
                            selected = selectedTab == index,
                            selectedContentColor = MaterialTheme.colorScheme.onPrimary,
                            unselectedContentColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f),
                            onClick = { viewModel.onTabSelected(index) },
                            text = { Text(tile) }
                        )
                    }
                } // fin del TabRow
            } // fin de la columna
        },// fin del topBar

        floatingActionButton = {
            var fabState by remember { mutableStateOf("add") }
            FloatingActionButton(onClick = {
                if (fabState == "add") {
                    fabState = "done"
                    // back after 2 seconds
                    scope.launch {
                        delay(2000)
                        fabState = "add"
                    }
                }
            },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Crossfade(
                    targetState = fabState,
                    animationSpec = tween(durationMillis = 300),
                    label = "fab icon transition"
                ) {
                    state ->
                    when (state) {
                        "add" -> Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add playlist",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                        "done" -> Text(
                            text = "Done",
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }
        }
    ) // fin del scaffold
    {// fin del scaffold
        paddingValues ->
        // change playlists to filteredPlaylist when tab is changed with lazy vertical grid
        if (filteredPlaylist.isEmpty()) {
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
                contentAlignment = Alignment.Center)
            {
                Text(
                    text = if (selectedTab == 1) "No favorite playlists yet"
                    else
                        "No playlists yet, click the button  + below to add one"
                )
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
                items(filteredPlaylist, key = { it.id }) { playlist ->
                    PlaylistCard(
                        playlist = playlist,
                        onClick = { onPlaylistClick(playlist) },
                        onLongClick = { selectedPlaylist = playlist }
                    )
                }
            } // fin del LazyVerticalGrid
        } // fin del else de  filteredPlaylist.isEmpty()
    } // fin del scaffold
    selectedPlaylist?.let { playlist ->
        PlaylistBottomSheet(
            playlist = playlist,
            onDismiss = { selectedPlaylist = null },
            onToggleFavorite = { viewModel.toggleFavorite(it) },
            onDelete = { viewModel.deletePlaylist(it) }
        )
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