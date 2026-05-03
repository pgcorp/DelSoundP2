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


    private val _selectedTab = MutableStateFlow(0)




    // Publicas para acceder desde la UI y el ViewModel de LibraryScreen
    val playlists: StateFlow<List<Playlist>> = PlaylistRepository.playlists

    // State para el tab seleccionado en la LibraryScreen 0 = all, 1 = favorites
    val selectedTab: StateFlow<Int> = _selectedTab.asStateFlow()

    // Filtrar playlists según el tab seleccionado
    val filteredPlaylist: StateFlow<List<Playlist>> = combine(
        PlaylistRepository.playlists, _selectedTab
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



    // Funciones para manejar la lista de favoritos

    fun toggleFavorite(playlist: Playlist) {
        PlaylistRepository.toggleFavorite(playlist)
    }
    fun deletePlaylist(playlist: Playlist) {
        PlaylistRepository.deletePlaylist(playlist)
    }


    //
    fun onTabSelected(index: Int) {
        _selectedTab.value = index
    }
}