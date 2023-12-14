package com.yavs.swapify.data.model.spotify

import com.google.gson.annotations.SerializedName
import com.yavs.swapify.data.model.Playlist

data class SpotifyPlaylist(
    val id: String,
    val images: List<Image>,
    val name: String,
    val tracks: Tracks,
    val uri: String
){
    inner class Image(
        val url: String="",
    )
    inner class Tracks(
        val total: Long,
    )

    data class Item(
        val id: String
    )

    data class Owner(
        @SerializedName("external_urls")
        val href: String,
        val id: String,
        val type: String,
        val uri: String,
        @SerializedName("display_name")
        val displayName: String,
    )

    fun toPlaylist() = Playlist(
        id = id,
        title = name,
        link = uri,
        picture = images.getOrElse(0){Image()}.url,
        nbTracks = tracks.total
    )

}

