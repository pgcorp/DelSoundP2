package com.example.delsound.ui

import androidx.lifecycle.ViewModel
import com.example.delsound.ui.models.Playlist
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class LibraryViewModel : ViewModel() {
    private val _playlists = MutableStateFlow(
        listOf(
            Playlist(1, "Favourites", "Rock", 15, "#E91E63", isFavorite = true),
            Playlist(2, "Pop Hits", "Pop", 20, "#2196F3"),
            Playlist(3, "Hip Hop Jams", "Hip Hop", 12, "#FFC107"),
            Playlist(4, "Indie Radio", "Indie", 8, "#4CAF50"),
            Playlist(5, "Classical Music", "Classical", 18, "#9C27B0"),
            Playlist(6, "Metal Covers", "Metal", 25, "#F44336")
        )
    )
    val playlists: StateFlow<List<Playlist>> = _playlists.asStateFlow()

    // Funciones para manejar la lista de favoritos
    fun toggleFavorite(playlist: Playlist) {
        _playlists.value = _playlists.value.map {
            if (it.id == playlist.id) {
                it.copy(isFavorite = !it.isFavorite)
            } else {
                it
            }
        }
    }

    fun deletePlaylist(playlist: Playlist) {
        _playlists.value = _playlists.value.filter { it.id != playlist.id }
    }

}