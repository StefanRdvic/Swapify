package com.yavs.swapify.data.model.deezer

import com.google.gson.annotations.SerializedName
import com.yavs.swapify.data.model.Playlist
import com.yavs.swapify.data.model.User

data class DeezerPlaylist(
    val id: Long,
    val title: String,
    val link: String,
    val picture:String,
    val creator: User = User(),
    @SerializedName("nb_tracks")
    val nbTracks: Long
){
    fun toPlaylist() = Playlist(
        id = id.toString(),
        title = title,
        link = link,
        picture = picture,
        nbTracks = nbTracks
    )
}