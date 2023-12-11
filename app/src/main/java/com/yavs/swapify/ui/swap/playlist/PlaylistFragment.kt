//package com.yavs.swapify.ui.swap.playlist
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.viewModels
//import androidx.recyclerview.widget.RecyclerView
//import com.yavs.swapify.R
//import com.yavs.swapify.data.model.Playlist
//
//class PlaylistFragment : Fragment() {
//    private val viewModel: PlaylistViewModel by viewModels { PlaylistViewModel.Factory }
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_playlist, container, false)
//    }
//
//    fun setPLaylists(view: View, playlists: List<Playlist>) {
//        val recyclerView = view.findViewById<RecyclerView>(R.id.userRecycler)
//        recyclerView.adapter =  PlaylistAdapter(view.context, playlists, viewModel.currentPlatform)
//        recyclerView.setHasFixedSize(true)
//    }
//
//}