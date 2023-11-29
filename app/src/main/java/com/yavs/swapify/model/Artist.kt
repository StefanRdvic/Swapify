package com.yavs.swapify.model

import kotlinx.serialization.Serializable

@Serializable
data class Artist( val id: Long, val name: String, val link: String)
