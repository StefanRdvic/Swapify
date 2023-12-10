package com.yavs.swapify.ui.swap

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
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
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }
        }

        val settingsButton = view.findViewById<View>(R.id.settingsButton)

        settingsButton.setOnClickListener {
            findNavController().navigate(R.id.action_swapFragment_to_settingsFragment)
        }

        val swapButton = view.findViewById<View>(R.id.swapButton)

        swapButton.setOnClickListener {
            // check if two tokens exist in shared preferences
            // if not toast go settings to connect
            // else go to loading fragment

            findNavController().navigate(
                SwapFragmentDirections.actionSwapFragmentToLoadingFragment(
                    spinnerFrom.selectedItem.toString(),
                    spinnerTo.selectedItem.toString())
            )
        }

    }



}