package com.yavs.swapify.data.model.spotify

import com.google.gson.annotations.SerializedName
import com.yavs.swapify.data.model.Track


data class SpotifyTrack(
    val album: Album,
    val artist: Artist,
    val id: String,
    val name: String,
    val uri: String,
    @SerializedName(value="durationMs", alternate = ["duration_ms"])
    val durationMs: Long,
) {
    inner class Album(
        val images: List<Image>
    )

    inner class Artist(
        val name: String,
    )

    inner class Image(
        val url: String
    )

fun toTrack() = Track(
    title = name,
    duration = durationMs,
    artistName = artist.name,
    image = album.images.getOrNull(2)?.url //Send 64,64 image
    )
}