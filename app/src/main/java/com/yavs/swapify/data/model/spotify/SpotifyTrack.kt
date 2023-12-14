package com.yavs.swapify.data.model.spotify

import com.google.gson.annotations.SerializedName
import com.yavs.swapify.data.model.Track


data class SpotifyTrack(
    val album: Album,
    val artists: List<Artist>,
    val id: String,
    val name: String,
    val uri: String,
    @SerializedName(value="durationMs", alternate = ["duration_ms"])
    val durationMs: Long,
    @SerializedName("preview_url")
    val previewUrl: String
) {
    inner class Album(
        val images: List<Image>
    )

    inner class Artist(
        val name: String="",
    )

    inner class Image(
        val url: String
    )

    fun toTrack() = Track(
        id = id,
        title = name,
        duration = durationMs,
        artistName = artists.getOrElse(0) { Artist() }.name,
        image = album.images.getOrNull(1)?.url, //Send 64,64 image
        preview = previewUrl
    )
}