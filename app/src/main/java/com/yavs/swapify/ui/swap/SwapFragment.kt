package com.yavs.swapify.ui.swap

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.yavs.swapify.R
import com.yavs.swapify.auth.TokenManager
import com.yavs.swapify.utils.Platform


class SwapFragment : Fragment() {
    private val tokenManager = TokenManager()
    private val viewModel: SwapViewModel by viewModels { SwapViewModel.Factory }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_swap, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setSpinner(view)

    }

    fun setSpinner(view: View){
        viewModel.setTokens(requireContext())
        val dropdown: Spinner = view.findViewById(R.id.platformSelect)
        val adapter: ArrayAdapter<Platform> = ArrayAdapter<Platform>(view.context, R.layout.platform_item, viewModel.availablePlateforms)
        dropdown.setAdapter(adapter)
    }


}