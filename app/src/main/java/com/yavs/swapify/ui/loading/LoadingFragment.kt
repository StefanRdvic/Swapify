package com.yavs.swapify.ui.loading

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.yavs.swapify.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoadingFragment : Fragment(R.layout.fragment_loading) {

    private val viewModel: LoadingViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args: LoadingFragmentArgs by navArgs()
        val from = args.fromPlatform
        val to = args.toPlatform
        view.findViewById<TextView>(R.id.textTest).text = "from $from to $to"

        val backButton = view.findViewById<Button>(R.id.backButton)

        backButton.setOnClickListener {
            findNavController().navigate(R.id.action_loadingFragment_to_swapFragment)
        }

        viewModel.getUserToken(from, to)
    }

}