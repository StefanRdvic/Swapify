package com.yavs.swapify.data.model

import com.google.gson.annotations.SerializedName


data class Playlist(
    val id: String,
    val title: String,
    val link: String,
    val picture:String,
    val creator: User = User(),
    @SerializedName(value="nbTracks", alternate= ["nb_tracks"])
    val nbTracks: Long
)
