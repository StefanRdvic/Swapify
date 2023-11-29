package com.yavs.swapify

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.liveData
import androidx.recyclerview.widget.RecyclerView
import com.yavs.swapify.auth.TokenManager
import com.yavs.swapify.model.User
import com.yavs.swapify.service.PlatformManager
import com.yavs.swapify.ui.user.UserAdapter
import com.yavs.swapify.utils.Platform
import kotlinx.coroutines.Dispatchers

class SettingsFragment : Fragment() {

    private var accounts = mutableListOf<User>()
    private val tokenManager = TokenManager()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getAccounts().observe(viewLifecycleOwner) {
            accounts=it
            initView(requireView())
        }

    }

    private fun initView(view: View){
        val recyclerView = view.findViewById<RecyclerView>(R.id.userRecycler)
        recyclerView.adapter =  UserAdapter(view.context, accounts, tokenManager)
        recyclerView.setHasFixedSize(true)
    }
    private fun getAccounts() = liveData(Dispatchers.IO) {
        val accounts = mutableListOf<User>()
        for (platform in Platform.entries) {
            val token = tokenManager.getToken(requireActivity(),platform)
            try {
                val result = PlatformManager.getService(platform).getUser(token)
                result.getOrNull()?.let { accounts.add(it) }
            } catch (e: Exception) {
                Log.d("error connecting to api", e.toString())
                accounts.add(User(null, null, null, null, null, platform))
            }
        }
        emit(accounts)
    }




}