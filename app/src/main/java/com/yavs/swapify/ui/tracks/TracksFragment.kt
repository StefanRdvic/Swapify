package com.yavs.swapify.ui.tracks

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.yavs.swapify.R
import com.yavs.swapify.utils.Platform
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.withCreationCallback

@AndroidEntryPoint
class TracksFragment : Fragment(R.layout.fragment_tracks) {
    private val args: TracksFragmentArgs by navArgs()

    private val viewModel by viewModels<TracksViewModel>(extrasProducer = {
        defaultViewModelCreationExtras.withCreationCallback<TracksViewModel.Factory> {
            it.create(Platform.valueOf(args.fromPlatform),Platform.valueOf(args.toPlatform),args.playlistId,args.playlistName)
        }
    })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.tracksRecycler)

        recyclerView.adapter = TracksAdapter(mutableListOf(), colorSelector = {0}) {}

        viewModel.tracks.observe(viewLifecycleOwner){
            val recyclerView = view.findViewById<RecyclerView>(R.id.tracksRecycler)
            view.findViewById<Button>(R.id.tracksConfirm).isEnabled = it.isNotEmpty()

            recyclerView.adapter = TracksAdapter(it.toMutableList(), colorSelector = {id -> ContextCompat.getColor(requireContext(), id)}) {

                val trackIds = mutableListOf<String>()
                for(track in viewModel.tracks.value!!)
                {
                    trackIds.add(track.id)
                }
                view.findViewById<Button>(R.id.tracksConfirm).isEnabled = trackIds.isNotEmpty()

            }
        }

        view.findViewById<TextView>(R.id.selectTracksTextView).text= getString(R.string.what_we_found_on, args.toPlatform)
        view.findViewById<ImageButton>(R.id.backToPlaylistButton).setOnClickListener {
            findNavController().navigate(TracksFragmentDirections.actionTracksFragmentToPlaylistsFragment(
                args.fromPlatform,
                args.toPlatform
            ))
        }

        view.findViewById<Button>(R.id.tracksConfirm).setOnClickListener{
            viewModel.createPlaylistSwap()
        }
        viewModel.playlistCreated.observe(viewLifecycleOwner){
            if(it)findNavController().navigate(TracksFragmentDirections.actionTracksFragmentToSwapFragment2())
            else println("Error")
        }


    }



}