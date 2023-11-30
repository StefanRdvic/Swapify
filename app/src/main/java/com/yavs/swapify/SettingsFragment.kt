package com.yavs.swapify

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.yavs.swapify.auth.TokenManager
import com.yavs.swapify.model.User
import com.yavs.swapify.service.DeezerService
import com.yavs.swapify.ui.user.UserAdapter
import com.yavs.swapify.utils.Platform

class SettingsFragment : Fragment() {
    private val viewModel: MainViewModel by viewModels { MainViewModel.Factory }
    private val tokenManager = TokenManager()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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