package com.yavs.swapify.ui.swap

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.yavs.swapify.auth.TokenManager
import com.yavs.swapify.ui.settings.SettingsViewModel
import com.yavs.swapify.utils.Platform

class SwapViewModel(private val tokenManager: TokenManager) : ViewModel() {
    private var tokens : MutableMap<String, String>? = mutableMapOf<String, String>()
    var availablePlateforms = mutableListOf<Platform>()
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                SettingsViewModel(
                    TokenManager()
                )
            }
        }
    }

    fun setTokens(context: Context){
        tokens = tokenManager.getAll(context) as MutableMap<String, String>?
        availablePlateforms = mutableListOf()
        for (platform in Platform.entries){
            val token = tokens!![platform.accountName]
            if (token != null){
                availablePlateforms.add(platform)
            }
        }
    }


}