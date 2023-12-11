package com.yavs.swapify.data.model

import com.google.gson.annotations.SerializedName


data class Playlist(
    val id: Long,
    val title: String,
    val link: String,
    val picture:String,
    val creator: User,
    @SerializedName(value="name", alternate= ["person", "user"])
    val nbTracks: Int
)
