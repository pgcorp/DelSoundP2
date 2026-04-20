package com.example.delsound.ui.models

data class Song(
    val id: Int,
    val title: String,
    val artist: String,
    val durationSeconds: Int,
    val playlistId: Int
)
