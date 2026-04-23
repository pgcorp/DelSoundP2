package com.example.delsound.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.delsound.ui.SearchViewModel
import com.example.delsound.ui.models.Playlist
import com.example.delsound.ui.theme.DelSoundTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(viewModel: SearchViewModel = viewModel(), onPlaylistClick: (Playlist) -> Unit)
{
    val query by viewModel.query.collectAsStateWithLifecycle()
    val isSearchActive by viewModel.isSearchActive.collectAsStateWithLifecycle()
    val durationRange by viewModel.durationRange.collectAsStateWithLifecycle()
    val searchResults by viewModel.searchResults.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        // SearchBar composable with search functionality and search results
        SearchBar(
            query = query,
            onQueryChange =  viewModel::onQueryChange,
            onSearch = { /* pendiente */ },
            active = isSearchActive,
            onActiveChange = viewModel::onSearchActiveChange,
            placeholder = { Text( "Search playlists") },
            leadingIcon = { Icon(
                Icons.Default.Search,
                contentDescription = "Search") },
        )
        {
            LazyColumn {
                items(searchResults, key = { it.id }) { playlist ->
                    ListItem(
                        headlineContent = { Text(playlist.name) },
                        supportingContent = { Text("${playlist.songCount} songs ${playlist.genre}") },
                        modifier = Modifier.clickable { onPlaylistClick(playlist) }
                    )
                }
            } // fin del LazyColumn
            Spacer(modifier = Modifier.height(24.dp))

            // RangeSlider para filtrar por duración
            Text("Duration",
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            val minMinutes = (durationRange.start / 60).toInt()
            val maxMinutes = (durationRange.endInclusive / 60).toInt()
            Text("$minMinutes min — $maxMinutes min",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,)
            RangeSlider(
                value         = durationRange,
                onValueChange = viewModel::onDurationRangeChange,
                valueRange    = 0f..5400f,
                steps         = 9,
                modifier      = Modifier.fillMaxWidth()
            )
            HorizontalDivider(modifier = Modifier.padding(top = 8.dp))
        }

    }
}

private val previewSearchViewModel = SearchViewModel()

@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    DelSoundTheme {
        SearchScreen(
            viewModel = previewSearchViewModel,
            onPlaylistClick = {}
        )
    }
}