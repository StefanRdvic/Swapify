package com.yavs.swapify.ui.swap

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.yavs.swapify.R


class SwapFragment : Fragment(R.layout.fragment_swap) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val spinnerFrom = view.findViewById<Spinner>(R.id.fromSpinner)
        val spinnerTo = view.findViewById<Spinner>(R.id.toSpinner)

        arrayOf(spinnerFrom, spinnerTo).forEach {
            it.adapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.platforms,
                R.layout.spinner_platform_item
            ).also { adapter ->
                adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
            }
        }

        val settingsButton = view.findViewById<ImageButton>(R.id.settingsButton)

        settingsButton.setOnClickListener {
            findNavController().navigate(R.id.action_swapFragment_to_settingsFragment)
        }

        val swapButton = view.findViewById<Button>(R.id.swapButton)

        swapButton.setOnClickListener {
//             check if two tokens exist in shared preferences
//             if not toast go settings to connect
//             else go to loading fragment
            val action = SwapFragmentDirections.actionSwapFragmentToPlaylistFragment(
                spinnerFrom.selectedItem.toString(),
                spinnerTo.selectedItem.toString()
            )
            findNavController().navigate(action)
        }

//        view.findViewById<Button>(R.id.buttonTest).setOnClickListener{
//            findNavController().navigate(
//                R.id.action_swapFragment_to_mainFragment
//            )
//        }

    }



}