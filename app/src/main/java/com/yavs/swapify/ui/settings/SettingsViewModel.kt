package com.yavs.swapify.ui.settings

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.yavs.swapify.auth.TokenManager
import com.yavs.swapify.model.User
import com.yavs.swapify.service.PlatformManager
import com.yavs.swapify.utils.Platform
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class SettingsViewModel(private val tokenManager: TokenManager) : ViewModel() {
    private val job = SupervisorJob()
    private val mainViewScope = CoroutineScope(Dispatchers.IO + job)

    val accounts = MutableLiveData<MutableList<User>>()
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                SettingsViewModel(
                    TokenManager()
                )
            }
        }
    }

    fun saveToken(context: Context,code: String, plateform: Platform) {
        mainViewScope.launch {
            try {
                val token = PlatformManager.getService(plateform).getOAuthToken(code)
                tokenManager.saveToken(context,token, Platform.Deezer)
                getAccounts(context)
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

    fun getAccounts(context: Context)  {
        mainViewScope.launch {
            accounts.postValue(fetchAccounts(context))
        }
    }

    private fun fetchAccounts(context: Context): MutableList<User>? {
        val accounts = mutableListOf<User>()
        for (platform in Platform.entries) {
            val token = tokenManager.getToken(context,platform)
            try {
                val result = PlatformManager.getService(platform).getUser(token)
                accounts.add(result.getOrThrow())
            } catch (e: Exception) {
                Log.d("error connecting to api", e.toString())
                accounts.add(User(platform= platform))
            }
        }
        return accounts
    }



}