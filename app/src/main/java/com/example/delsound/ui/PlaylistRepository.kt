package com.example.delsound.ui

import android.util.Log
import com.example.delsound.ui.models.Playlist
import com.example.delsound.ui.models.Song
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object PlaylistRepository {

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
    val songs = listOf(
        Song(1, "Bohemian Rhapsody", "Queen", 354, 1),
        Song(2, "Blinding Lights", "The Weeknd", 200, 2),
        Song(3, "HUMBLE.", "Kendrick Lamar", 177, 3),
        Song(4, "Do I Wanna Know?", "Arctic Monkeys", 272, 4),
        Song(5, "Moonlight Sonata", "Beethoven", 337, 5),
        Song(6, "Master of Puppets", "Metallica", 515, 6)
    )
    val playlists: StateFlow<List<Playlist>> = _playlists.asStateFlow()

    fun toggleFavorite(playlist: Playlist) {
        _playlists.value = _playlists.value.map {
            if (it.id == playlist.id) it.copy(isFavorite = !it.isFavorite) else it
        }
    }

    fun deletePlaylist(playlist: Playlist) {
        _playlists.value = _playlists.value.filter { it.id != playlist.id }
        Log.d("PlaylistRepository", "deleted: ${playlist.name}, remaining: ${_playlists.value.size}")
    }
}