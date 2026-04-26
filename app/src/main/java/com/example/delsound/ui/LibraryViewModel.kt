package com.example.delsound.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.delsound.ui.models.Playlist
import com.example.delsound.ui.models.Song
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class LibraryViewModel : ViewModel() {

    // Privados para mantener la lista de playlists y el tab seleccionado
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

    private val _selectedTab = MutableStateFlow(0)




    // Publicas para acceder desde la UI y el ViewModel de LibraryScreen
    val playlists: StateFlow<List<Playlist>> = _playlists.asStateFlow()

    // State para el tab seleccionado en la LibraryScreen 0 = all, 1 = favorites
    val selectedTab: StateFlow<Int> = _selectedTab.asStateFlow()

    // Filtrar playlists según el tab seleccionado
    val filteredPlaylist: StateFlow<List<Playlist>> = combine(
        _playlists, _selectedTab
    ) { playlists, tabIndex ->
        when (tabIndex) {
            0 -> playlists
            1 -> playlists.filter { it.isFavorite }
            else -> playlists
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val songs = listOf(
        Song(1, "Bohemian Rhapsody", "Queen", 354, 1),
        Song(2, "Blinding Lights", "The Weeknd", 200, 2),
        Song(3, "HUMBLE.", "Kendrick Lamar", 177, 3),
        Song(4, "Do I Wanna Know?", "Arctic Monkeys", 272, 4),
        Song(5, "Moonlight Sonata", "Beethoven", 337, 5),
        Song(6, "Master of Puppets", "Metallica", 515, 6)
    )

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

    // Delete playlist function to remove a playlist from the list
    fun deletePlaylist(playlist: Playlist) {
        _playlists.value = _playlists.value.filter { it.id != playlist.id }
    }

    //
    fun onTabSelected(index: Int) {
        _selectedTab.value = index
    }
}