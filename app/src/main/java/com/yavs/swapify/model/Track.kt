package com.yavs.swapify.model

import kotlinx.serialization.Serializable

@Serializable
data class Track( val id: Long, val title: String, val link: String, val image: String,
                  val preview: String, val tracks: List<Track>)
