package com.yavs.swapify.data.model.deezer

import com.google.gson.annotations.SerializedName
import com.yavs.swapify.data.model.Track

data class DeezerTrack (
    val title: String = "",
    val album: Album,
    val duration: Long = 0,
    @SerializedName("artist")
    val artist : Artist?,
    val preview: String = ""
){
    inner class Artist(
        val name: String
    )

    inner class Album (
        val cover: String
    )
    fun toTrack() = Track(
        title,
        album.cover,
        duration,
        artist?.name?:"",
        preview
    )
}