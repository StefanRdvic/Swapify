package com.yavs.swapify.data.model

import java.time.LocalDateTime

data class Token (
    val access: String,
    val expirationDate: LocalDateTime? = LocalDateTime.MAX,
)
