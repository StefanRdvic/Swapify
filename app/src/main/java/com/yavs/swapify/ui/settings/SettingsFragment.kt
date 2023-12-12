package com.yavs.swapify.ui.settings

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.yavs.swapify.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private lateinit var adapter: SettingsAdapter

    private val viewModel: SettingsViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<View>(R.id.backButton).setOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_swapFragment)
        }

        val recyclerView = view.findViewById<RecyclerView>(R.id.userRecycler)

        recyclerView.adapter = SettingsAdapter(mutableListOf(), onDisconnectButtonClick = {}, onConnectButtonClick = {})

        viewModel.fetchedUsers.observe(viewLifecycleOwner) {
            adapter = SettingsAdapter(it,
                onDisconnectButtonClick = {platform ->
                    viewModel.disconnect(platform)
                },
                onConnectButtonClick = { platform ->
                    viewModel.startOAuthActivity(platform){ intent ->
                        requireContext().startActivity(intent)
                    }
                })
            recyclerView.swapAdapter(
                adapter, false
            )
        }

        viewModel.userUpdated.observe(viewLifecycleOwner) {
            if (it != null) {
                val (index, user) = it
                adapter.notifyItemChanged(index, user)
            }
        }

    }
}