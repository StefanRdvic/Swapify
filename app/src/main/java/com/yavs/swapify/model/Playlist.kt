package com.yavs.swapify.model

import kotlinx.serialization.Serializable

@Serializable
data class Playlist( val id: Long, val title: String, val link: String, val picture:String, val creator: User)
