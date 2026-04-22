package com.example.delsound.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.delsound.ui.models.Playlist
import com.example.delsound.ui.theme.DelSoundTheme
import androidx.core.graphics.toColorInt

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PlaylistCard(playlist: Playlist, onClick: () -> Unit, onLongClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)   // hace la tarjeta cuadrada
            .combinedClickable(onClick = onClick, onLongClick = onLongClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Portada de color generada desde el HEX del modelo
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)   // ocupa all el espacio disponible
                    .background(
                        Color(playlist.colorHex.toColorInt())
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.MusicNote, contentDescription = "Playlist cover",
                    tint = Color.White.copy(alpha = 0.7f),
                    modifier = Modifier.size(32.dp))
            }
            // Información de la playlist
            Column(modifier = Modifier.padding(8.dp)) {
                Text(playlist.name,
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis)
                Text("${playlist.songCount} songs",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}


@Preview(showBackground = true, widthDp = 200)
@Composable
fun PlaylistCardPreview() {
    DelSoundTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            PlaylistCard(
                playlist = Playlist(
                    id = 1,
                    name = "Rock Classics",
                    genre = "Rock",
                    songCount = 15,
                    colorHex = "#E91E63" // Un color rosa para el ejemplo
                ),
                onClick = {},
                onLongClick = {}
            )
        }
    }
}