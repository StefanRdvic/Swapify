package com.yavs.swapify.data.model.spotify

import com.yavs.swapify.data.model.Playlist

data class SpotifyPlaylist(
    val id: String,
    val images: List<Image>,
    val name: String,
    val tracks: Tracks,
    val uri: String,
){
    inner class Image(
        val url: String="",
    )
    inner class Tracks(
        val total: Long,
    )
    fun toPlaylist() = Playlist(
        id = id,
        title = name,
        link = uri,
        picture = images.getOrElse(0){Image()}.url,
        nbTracks = tracks.total
    )

}

