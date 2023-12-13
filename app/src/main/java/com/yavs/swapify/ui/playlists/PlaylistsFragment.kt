package com.yavs.swapify.ui.playlists

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.yavs.swapify.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlaylistsFragment: Fragment(R.layout.fragment_playlist) {
    private val viewModel: PlaylistsViewModel by viewModels()
    private val args: PlaylistsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.playlistRecycler)
        recyclerView.adapter =  PlaylistAdapter(emptyList()) {
            showConfirm(view, it)
        }
        viewModel.playlists.observe(viewLifecycleOwner){
            val adapter = PlaylistAdapter(it) { position ->
                showConfirm(view, position)
            }
            recyclerView.swapAdapter(adapter,false)
        }


        try{
            viewModel.setPlaylist(args.fromPlatform.lowercase())
        }catch (_: Exception){
            Toast.makeText(activity, "platform not connected", Toast.LENGTH_SHORT).show()
            findNavController().navigate(PlaylistsFragmentDirections.actionPlaylistFragmentToSettingsFragment())
        }




    }

    private fun showConfirm(view: View, position: Int){
        val button = view.findViewById<Button>(R.id.playlistConfirm)
        button.visibility=if (position==-1) View.INVISIBLE else View.VISIBLE
    }

}