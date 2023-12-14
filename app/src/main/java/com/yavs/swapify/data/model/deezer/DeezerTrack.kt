package com.yavs.swapify.data.model.deezer

import com.google.gson.annotations.SerializedName
import com.yavs.swapify.data.model.Track

data class DeezerTrack (
    val title: String = "",
    val image: String = "",
    val duration: Long = 0,
    @SerializedName("artist")
    val artist : Artist?,
    val preview: String = ""
){
    inner class Artist(
        val name: String
    )
    fun toTrack() = Track(
        title,
        image,
        duration,
        artist?.name?:"",
        preview
    )
}