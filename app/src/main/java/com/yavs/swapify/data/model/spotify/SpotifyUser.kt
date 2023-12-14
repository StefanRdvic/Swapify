package com.yavs.swapify.data.model.spotify

import com.google.gson.annotations.SerializedName
import com.yavs.swapify.data.model.User
import com.yavs.swapify.utils.Platform

data class SpotifyUser(
    val country: String,
    @SerializedName("display_name")
    val displayName: String,
    val href: String,
    val images: List<Image>,
    val product: String,
    val type: String,
    val uri: String
){

    inner class Image(
        val url: String=""
    )

    fun toUser() = User(
        name=displayName,
        link = uri,
        picture = images.getOrElse(0){Image()}.url,
        platform = Platform.Spotify
    )
}


