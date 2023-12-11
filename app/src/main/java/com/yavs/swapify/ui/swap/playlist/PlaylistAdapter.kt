//package com.yavs.swapify.ui.swap.playlist
//
//import android.content.Context
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Button
//import android.widget.ImageView
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//import com.squareup.picasso.Picasso
//import com.yavs.swapify.R
//import com.yavs.swapify.data.model.Playlist
//import com.yavs.swapify.utils.Platform
//
//class PlaylistAdapter(
//    private val context: Context,
//    private var playlists: List<Playlist>,
//    private val token: Platform?
//) : RecyclerView.Adapter<PlaylistAdapter.PlaylistViewHolder>() {
//    class PlaylistViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        val picture: ImageView = view.findViewById(R.id.playlistImage)
//        val name: TextView = view.findViewById(R.id.playlistName)
//        val creator: Button = view.findViewById(R.id.playlistCreator)
//        val nbTracks: TextView = view.findViewById(R.id.playlistNbTracks)
//    }
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
//        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.recycler_auth, parent, false)
//        return PlaylistViewHolder(adapterLayout)
//    }
//    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
//        val playlist = playlists[position]
//        Picasso.get().load(playlist.picture).into(holder.picture)
//        holder.name.text = playlist.title
//        holder.creator.text = context.getString(R.string.userFullName, playlist.creator.name, playlist.creator.lastName)
//        holder.nbTracks.text = context.getString(R.string.tracks, playlist.nbTracks.toString())
//    }
//    override fun getItemCount(): Int {
//        return playlists.size
//    }
//}