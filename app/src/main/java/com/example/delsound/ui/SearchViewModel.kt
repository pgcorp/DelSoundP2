package com.example.delsound.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.delsound.ui.models.Playlist
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn


class SearchViewModel : ViewModel() {
    // Lista de canciones para la pantalla de búsqueda
    // Variables Privadas para la lista de canciones y los filtros
    private val _allPlaylists = MutableStateFlow(
        listOf(
            Playlist(1, "Favourites", "Rock", 15, "#E91E63", isFavorite = true),
            Playlist(2, "Pop Hits", "Pop", 20, "#2196F3"),
            Playlist(3, "Hip Hop Jams", "Hip Hop", 12, "#FFC107"),
            Playlist(4, "Indie Radio", "Indie", 8, "#4CAF50"),
            Playlist(5, "Classical Music", "Classical", 18, "#9C27B0"),
            Playlist(6, "Metal Covers", "Metal", 25, "#F44336")
        )
    )
    private val _query = MutableStateFlow("")
    private val _isSearchActive = MutableStateFlow(false)
    private val _durationRange = MutableStateFlow(0f..5400f)

    // StateFlow para la lista de canciones filtrada por búsqueda y rangos de duración
    // Variables Publicas
    val query: StateFlow<String> = _query.asStateFlow()
    val isSearchActive: StateFlow<Boolean> = _isSearchActive.asStateFlow()
    val durationRange: StateFlow<ClosedFloatingPointRange<Float>> = _durationRange.asStateFlow()

    // filtered results by text and long time at the same time thanks to Combine
    val searchResults: StateFlow<List<Playlist>> = combine(_allPlaylists, _query, _durationRange)
    { playlists: List<Playlist>, query: String, range: ClosedFloatingPointRange<Float> ->
        playlists.filter { playlist ->
            // Filter 1: search by name or genre
            val matchesQuery = query.isBlank() ||
                    playlist.name.contains(query, ignoreCase = true) ||
                    playlist.genre.contains(query, ignoreCase = true)
            // Filter 2: duration range
            val totalDuration = playlist.songCount * 180f // 3 minutes / song
            val matchesDuration =
                totalDuration >= range.start && totalDuration <= range.endInclusive
            matchesQuery && matchesDuration // both filters must be true
        }
    } .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000),emptyList())

    // functions to update the filters and query in the ViewModel to update the search results
    fun onQueryChange(query: String) { _query.value = query }
    fun onSearchActiveChange(active: Boolean) { _isSearchActive.value = active }
    fun onDurationRangeChange(range: ClosedFloatingPointRange<Float>) { _durationRange.value = range }
}

