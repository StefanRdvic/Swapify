package com.yavs.swapify.ui.swap

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.Spinner
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.yavs.swapify.R
import com.yavs.swapify.utils.Platform
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainFragment : Fragment(R.layout.main_swap) {
    private val mainViewModel: MainViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val spinnerFrom = view.findViewById<Spinner>(R.id.fromSpinner)
        val spinnerTo = view.findViewById<Spinner>(R.id.toSpinner)
        val swapButton = view.findViewById<Button>(R.id.swapButton)

        arrayOf(spinnerFrom, spinnerTo).forEach {
            it.adapter = ArrayAdapter.createFromResource(
                requireContext(),
                if(it.id == R.id.fromSpinner) R.array.platforms_from else R.array.platforms_to,
                R.layout.spinner_platform_item
            ).also { adapter ->
                adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
            }

            it.onItemSelectedListener = object : OnItemSelectedListener{
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val from = Platform.valueOf(spinnerFrom.selectedItem.toString())
                    val to = Platform.valueOf(spinnerTo.selectedItem.toString())
                    val isOk = from != to &&
                            mainViewModel.isTokenAvailable(from) &&
                            mainViewModel.isTokenAvailable(to)

                    swapButton.isEnabled = isOk
                    swapButton.setTextColor(ContextCompat.getColor(requireContext(), if (isOk) R.color.white else R.color.primary_text_disabled))
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    swapButton.isEnabled = false
                    swapButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.primary_text_disabled))
                }

            }


        }

        view.findViewById<ImageButton>(R.id.settingsButton).setOnClickListener {
            findNavController().navigate(R.id.action_swapFragment_to_settingsFragment)
        }


        swapButton.setOnClickListener {
            findNavController().navigate(
                MainFragmentDirections.actionSwapFragmentToPlaylistFragment(
                    spinnerFrom.selectedItem.toString(),
                    spinnerTo.selectedItem.toString())
            )
        }

    }

}