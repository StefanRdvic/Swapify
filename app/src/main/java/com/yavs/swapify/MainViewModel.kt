package com.yavs.swapify

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.yavs.swapify.auth.TokenManager
import com.yavs.swapify.service.PlatformManager
import com.yavs.swapify.utils.Platform
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class MainViewModel(val tokenManager: TokenManager) : ViewModel() {
    private val job = SupervisorJob()
    private val mainViewScope = CoroutineScope(Dispatchers.IO + job)
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                MainViewModel(
                    TokenManager()
                )
            }
        }
    }
    fun saveToken(context: Context,code: String, plateform: Platform) {
        mainViewScope.launch(Dispatchers.IO) {
            try {
                val token = PlatformManager.getService(plateform).getOAuthToken(code)
                tokenManager.saveToken(context,token, Platform.Deezer)
                val d=tokenManager.getToken(context, Platform.Deezer)
                if (d != null) {
                    Log.d("token",d)
                }
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }



}