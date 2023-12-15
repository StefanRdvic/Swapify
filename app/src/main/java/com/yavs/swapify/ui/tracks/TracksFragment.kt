package com.yavs.swapify.ui.tracks

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.yavs.swapify.R
import com.yavs.swapify.data.model.Track
import com.yavs.swapify.utils.Platform
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.withCreationCallback

@AndroidEntryPoint
class TracksFragment : Fragment(R.layout.fragment_tracks) {
    private val args: TracksFragmentArgs by navArgs()

    private var tracks = mutableListOf<Track>()
    private var allTracks = mutableListOf<Track>()
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
            view.findViewById<Button>(R.id.tracksConfirm).isEnabled = it.isNotEmpty()
            allTracks = it.toMutableList()
            tracks = allTracks
            recyclerView.adapter = TracksAdapter(it.toMutableList(), colorSelector = {id -> ContextCompat.getColor(requireContext(), id)}) { selected ->
                tracks = selected.map{pos -> allTracks[pos] }.toMutableList()
                view.findViewById<Button>(R.id.tracksConfirm).isEnabled = tracks.isNotEmpty()
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
            viewModel.createPlaylistSwap(tracks)
        }
        viewModel.playlistCreated.observe(viewLifecycleOwner){
            if(it){
                Toast.makeText(view.context,"Succesfully added playlist ${args.playlistName}",Toast.LENGTH_LONG).show()
                findNavController().navigate(TracksFragmentDirections.actionTracksFragmentToSwapFragment2())
            }
            else println("Error")
        }


    }



}