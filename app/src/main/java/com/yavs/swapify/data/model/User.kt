package com.yavs.swapify.data.model

import com.yavs.swapify.utils.Platform


data class User(
    val name: String?="",
    val lastName: String?="",
    val link: String?="",
    val picture: String?="",
    var platform: Platform?=null
)
