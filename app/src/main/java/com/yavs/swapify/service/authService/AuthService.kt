package com.yavs.swapify.service.authService

interface AuthService {
    fun getOAuthUrl(): String
    suspend fun getOAuthToken(code: String): String
}