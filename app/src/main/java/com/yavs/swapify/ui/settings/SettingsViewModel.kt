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
            users.postValue(Platform.values().map {
                services[it.name.lowercase()]?.getUser(sharedPreferencesRepository.getString(it.name, null))?.getOrNull() ?: User(platform = it)
            })
        }
    }

    fun startOAuthActivity(platform: Platform, startActivity: (Intent) -> Unit) {
        services[platform.name.lowercase()]?.getOAuthUrl()?.let {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(it)))
        }
    }

}