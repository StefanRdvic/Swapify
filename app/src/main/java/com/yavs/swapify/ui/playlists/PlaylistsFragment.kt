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
import com.yavs.swapify.R
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


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.playlistRecycler)
        val confirmButton = view.findViewById<Button>(R.id.playlistConfirm)

        recyclerView.adapter =  PlaylistsAdapter(emptyList(), colorSelector = {0}, onSelection =  {})



        view.findViewById<ImageButton>(R.id.backPlaylistsButton).setOnClickListener {
            findNavController().navigate(R.id.action_playlistsFragment_to_swapFragment)
        }

        viewModel.playlists.observe(viewLifecycleOwner){
            val adapter = PlaylistsAdapter(
                it,
                colorSelector = { id -> ContextCompat.getColor(requireContext(), id) }
            ) {
                pos ->
                confirmButton.isEnabled = pos != -1
                confirmButton.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        if (pos != -1) R.color.white else R.color.primary_text_disabled)
                )

                if (pos !=-1){
                    val playlistId = viewModel.playlists.value?.get(pos)?.id ?:""
                    if(playlistId.isBlank()){
                        confirmButton.setOnClickListener(null)
                    }else{
                        confirmButton.setOnClickListener{
                            findNavController().navigate(PlaylistsFragmentDirections.actionPlaylistsFragmentToTracksFragment(
                                playlistId,
                                args.fromPlatform,
                                args.toPlatform
                            ))
                        }
                    }
                }

            }

            recyclerView.swapAdapter(adapter,false)
        }


    }

}