package com.yavs.swapify.data.model


data class Playlist(
    val id: String,
    val title: String,
    val link: String,
    val picture:String,
    val creator: User = User(),
    val nbTracks: Long
)
