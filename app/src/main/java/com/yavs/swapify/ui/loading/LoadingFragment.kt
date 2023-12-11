package com.yavs.swapify.ui.loading

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.yavs.swapify.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoadingFragment : Fragment(R.layout.fragment_loading) {

    private val viewModel: LoadingViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.handleNewIntentData(requireActivity().intent.data)

        viewModel.navigationEvent.observe(viewLifecycleOwner){
            if(it){
                findNavController().navigate(R.id.action_loadingFragment_to_settingsFragment)
            }
        }
    }

}