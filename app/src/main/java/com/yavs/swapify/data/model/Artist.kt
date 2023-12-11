package com.yavs.swapify.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Artist( val id: Long, val name: String, val link: String)
