package com.yavs.swapify.ui.settings

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yavs.swapify.data.model.User
import com.yavs.swapify.data.repository.SharedPreferencesRepository
import com.yavs.swapify.service.PlatformService
import com.yavs.swapify.utils.Platform
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val services: Map<String, @JvmSuppressWildcards PlatformService>,
    private val sharedPreferencesRepository: SharedPreferencesRepository
) : ViewModel() {

    val users = MutableLiveData<List<User>>()

    init {
        syncUsers()
    }

    private fun syncUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            users.postValue(Platform.entries.map {
                var token = ""
                withContext(Dispatchers.Main) {
                    token = sharedPreferencesRepository.getString(it.name, null).toString()
                }
                services[it.name.lowercase()]?.getUser(token) ?: User(platform = it)
            })
        }
    }

    fun startOAuthActivity(platform: Platform, startActivity: (Intent) -> Unit) {
        services[platform.name.lowercase()]?.getOAuthUrl()?.let {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(it)))
        }
    }

}