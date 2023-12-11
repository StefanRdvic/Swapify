package com.yavs.swapify.data.model

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@OptIn(ExperimentalSerializationApi::class)
@Serializable
data class Playlist(
    val id: Long,
    val title: String,
    val link: String,
    val picture:String,
    val creator: User,
    @JsonNames("nb_tracks")
    val nbTracks: Int
)
