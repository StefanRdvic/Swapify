package com.yavs.swapify.ui.swap

import androidx.lifecycle.ViewModel
import com.yavs.swapify.data.repository.TokenRepository
import com.yavs.swapify.utils.Platform
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val tokenRepository: TokenRepository
) : ViewModel(){

    fun isTokenAvailable(platform: Platform): Boolean {
        return tokenRepository.get(platform)?.expirationDate?.isAfter(LocalDateTime.now())?: false
    }
}