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
    private val viewModel: SettingsViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<View>(R.id.backButton).setOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_swapFragment)
        }

        val recyclerView = view.findViewById<RecyclerView>(R.id.userRecycler)

        recyclerView.adapter = SettingsAdapter(listOf(), onDisconnectButtonClick = {}, onConnectButtonClick = {})

        viewModel.users.observe(viewLifecycleOwner) {
            recyclerView.adapter = SettingsAdapter(it,
                onDisconnectButtonClick = { platform ->
                    context?.getSharedPreferences("swapify", Context.MODE_PRIVATE)?.edit()?.remove(platform.name)?.apply()
                },
                onConnectButtonClick = { platform ->
                    viewModel.startOAuthActivity(platform){ intent ->
                        context?.startActivity(intent)
                    }
                })
        }


    }
}