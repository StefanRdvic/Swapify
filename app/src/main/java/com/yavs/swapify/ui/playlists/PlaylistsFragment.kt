package com.yavs.swapify.ui.playlists

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.yavs.swapify.R
import com.yavs.swapify.data.model.Playlist
import com.yavs.swapify.utils.Platform
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.withCreationCallback

@AndroidEntryPoint
class PlaylistsFragment: Fragment(R.layout.fragment_playlist) {

    private val args: PlaylistsFragmentArgs by navArgs()

    private val viewModel by viewModels<PlaylistsViewModel>(
        extrasProducer = {
            defaultViewModelCreationExtras.withCreationCallback<PlaylistsViewModel.Factory>{
                it.create(Platform.valueOf(args.fromPlatform))
            }
        }
    )

    private lateinit var selectedPlaylist: Playlist


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.playlistRecycler)
        val confirmButton = view.findViewById<Button>(R.id.playlistConfirm)
        val progressor = view.findViewById<CircularProgressIndicator>(R.id.progressBarPlaylist)

        recyclerView.adapter =  PlaylistsAdapter(emptyList()) {}



        view.findViewById<ImageButton>(R.id.backPlaylistsButton).setOnClickListener {
            findNavController().navigate(R.id.action_playlistsFragment_to_swapFragment)
        }

        viewModel.playlists.observe(viewLifecycleOwner){
            if(it.isEmpty()){
                progressor.visibility = View.VISIBLE
            }
            else{
                progressor.visibility = View.GONE
                val adapter = PlaylistsAdapter(it) {
                        pos ->
                    confirmButton.isEnabled = pos != -1
                    confirmButton.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            if (pos != -1) R.color.white else R.color.primary_text_disabled)
                    )
                    if (pos != -1)
                        selectedPlaylist = it[pos]
                }

                recyclerView.swapAdapter(adapter,false)
            }
        }

        confirmButton.setOnClickListener {
            findNavController()
                .navigate(
                    PlaylistsFragmentDirections.actionPlaylistsFragmentToTracksFragment(
                        args.fromPlatform,
                        args.toPlatform,
                        selectedPlaylist.id,
                        selectedPlaylist.title
                    )
                )
        }

    }

}