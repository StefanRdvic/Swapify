package com.yavs.swapify.ui.settings

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.OnNewIntentProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.yavs.swapify.R
import com.yavs.swapify.auth.TokenManager
import com.yavs.swapify.model.User
import com.yavs.swapify.service.DeezerService
import com.yavs.swapify.utils.Platform

class SettingsFragment : Fragment(R.layout.fragment_settings) {
    private val viewModel: SettingsViewModel by viewModels { SettingsViewModel.Factory }
    private val tokenManager = TokenManager()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val backButton = view.findViewById<View>(R.id.backButton)

        backButton.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_swapFragment)
        }

        checkOAuth(requireActivity().intent.data)
        viewModel.getAccounts(requireContext())
        viewModel.accounts.observe(viewLifecycleOwner){
            initView(requireView(),it)
        }
    }

    private fun initView(view: View, users: MutableList<User>){
        val recyclerView = view.findViewById<RecyclerView>(R.id.userRecycler)
        recyclerView.adapter =  UserAdapter(view.context, users, tokenManager)
        recyclerView.setHasFixedSize(true)
    }


    private fun checkOAuth(data: Uri?) {
        if(data!=null && data.toString().startsWith(DeezerService.REDIRECT_URL)){
            viewModel.saveToken(requireContext(), data.getQueryParameter("code")!!, Platform.Deezer)
            Log.i("saved token","saved token")
            Toast.makeText(context,"Successful Connection to deezer", Toast.LENGTH_SHORT).show()
        }
    }

}