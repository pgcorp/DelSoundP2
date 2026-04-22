package com.example.delsound.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.delsound.ui.models.Playlist
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaylistBottomSheet(
    playlist: Playlist,
    onDismiss: () -> Unit,
    onToggleFavorite: (playlist: Playlist) -> Unit,
    onDelete: (playlist: Playlist) -> Unit
){
    val sheetState = rememberModalBottomSheetState ()
    val scope = rememberCoroutineScope()

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier
                .padding(bottom = 32.dp)
                .fillMaxWidth()
        ) {
            Text(playlist.name,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp))
            HorizontalDivider()
            ListItem(
                headlineContent =
                    { Text(
                        if (playlist.isFavorite) "Remove from favorites"
                        else "Add to favorites"
                    )
                    },
                leadingContent = {
                    Icon(
                        imageVector =
                            if (playlist.isFavorite) Icons.Default.Favorite
                            else Icons.Default.FavoriteBorder,
                        contentDescription = null,
                        tint =
                            if (playlist.isFavorite) MaterialTheme.colorScheme.error
                            else MaterialTheme.colorScheme.onSurface
                    )
                },
                modifier = Modifier.clickable {
                    onToggleFavorite(playlist)
                scope.launch { sheetState.hide() }
                    .invokeOnCompletion {onDismiss()}
                }
            )
            // Option for deleting the playlist
            ListItem(
                headlineContent = { Text("Delete playlist",
                                  color = MaterialTheme.colorScheme.error)},
                leadingContent = {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error
                    )
                },
                modifier = Modifier.clickable {
                    onDelete(playlist)
                    scope.launch { sheetState.hide() }
                        .invokeOnCompletion { onDismiss() }
                }
            )
        }
    }
}