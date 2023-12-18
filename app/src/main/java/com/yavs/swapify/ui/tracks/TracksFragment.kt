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
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.yavs.swapify.R
import com.yavs.swapify.data.model.Track
import com.yavs.swapify.utils.Platform
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.withCreationCallback

@AndroidEntryPoint
class TracksFragment : Fragment(R.layout.fragment_tracks) {
    private val args: TracksFragmentArgs by navArgs()

    private var selectedTracks = mutableListOf<Track>()

    private val viewModel by viewModels<TracksViewModel>(
        extrasProducer = {
            defaultViewModelCreationExtras.withCreationCallback<TracksViewModel.Factory> {
                it.create(
                    Platform.valueOf(args.fromPlatform),
                    Platform.valueOf(args.toPlatform),
                    args.playlistId,
                    args.playlistName
                )
            }
        }
    )

    enum class SelectionType {
        SELECTION, DESELECTION
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.tracksRecycler)
        val progressor = view.findViewById<CircularProgressIndicator>(R.id.progressBarTrack)

        recyclerView.adapter = TracksAdapter(mutableListOf()) {_, _ ->}

        viewModel.tracks.observe(viewLifecycleOwner){
            if(it.isEmpty()){
                progressor.visibility = View.VISIBLE
            }
            else{
                progressor.visibility = View.GONE
                selectedTracks = it.toMutableList()
                val adapter = TracksAdapter(it) { pos, selectionType ->
                    if(selectionType == SelectionType.SELECTION)
                        selectedTracks.add(it[pos])
                    else
                        selectedTracks.remove(it[pos])
                    setConfirmButton()
                }
                recyclerView.swapAdapter(adapter, false)
                setConfirmButton()
            }
        }

        view.findViewById<TextView>(R.id.selectTracksTextView).text = getString(R.string.what_we_found_on, args.toPlatform)

        view.findViewById<ImageButton>(R.id.backToPlaylistButton).setOnClickListener {
            findNavController().navigate(TracksFragmentDirections.actionTracksFragmentToPlaylistsFragment(
                args.fromPlatform,
                args.toPlatform
            ))
        }

        view.findViewById<Button>(R.id.tracksConfirm).setOnClickListener{
            viewModel.createPlaylistSwap(selectedTracks)
        }

        viewModel.playlistCreated.observe(viewLifecycleOwner){
            if(it){
                Toast.makeText(view.context,"Succesfully added ${args.playlistName} to ${args.toPlatform}",Toast.LENGTH_LONG).show()
                findNavController().navigate(TracksFragmentDirections.actionTracksFragmentToSwapFragment2())
            }
            else {
                Toast.makeText(view.context,"Something wrong happened try again in a few minute",Toast.LENGTH_LONG).show()
                findNavController().navigate(TracksFragmentDirections.actionTracksFragmentToSwapFragment2())
            }
        }

    }

    private fun setConfirmButton(){
        val confirmButton = view?.findViewById<Button>(R.id.tracksConfirm)

        confirmButton?.isEnabled = selectedTracks.isNotEmpty()
        confirmButton?.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                if (selectedTracks.isNotEmpty()) R.color.white else R.color.primary_text_disabled)
        )
    }
}