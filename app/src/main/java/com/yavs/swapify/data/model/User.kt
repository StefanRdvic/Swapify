package com.yavs.swapify.data.model

import com.yavs.swapify.utils.Platform
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Long?=null,
    val name: String?="",
    val lastName: String?="",
    val link: String?="",
    val picture: String?="",
    var platform: Platform?=null
)
