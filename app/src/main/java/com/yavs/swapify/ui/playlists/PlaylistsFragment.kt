package com.yavs.swapify.ui.playlists

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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

        recyclerView.adapter =  PlaylistsAdapter(emptyList()) { }

        viewModel.playlists.observe(viewLifecycleOwner){
            val adapter = PlaylistsAdapter(it) { pos ->
                view.findViewById<Button>(R.id.playlistConfirm).visibility= if (pos==-1) View.INVISIBLE else View.VISIBLE
            }
            recyclerView.swapAdapter(adapter,false)
        }
    }

}