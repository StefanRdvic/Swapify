package com.yavs.swapify.ui.swap

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.yavs.swapify.R
import com.yavs.swapify.auth.TokenManager


class SwapFragment : Fragment() {
    private val tokenManager = TokenManager()
    private val viewModel: SwapViewModel by viewModels { SwapViewModel.Factory }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_swap, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }




}