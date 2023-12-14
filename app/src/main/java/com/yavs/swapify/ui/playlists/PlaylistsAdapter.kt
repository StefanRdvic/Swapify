package com.yavs.swapify.ui.playlists

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.yavs.swapify.R
import com.yavs.swapify.data.model.Playlist

class PlaylistsAdapter(
    private val playlists: List<Playlist>,
    private val onSelection: (pos: Int) -> Unit
) : RecyclerView.Adapter<PlaylistsAdapter.PlaylistsViewHolder>() {

    private var selected = RecyclerView.NO_POSITION

    inner class PlaylistsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val picture: ImageView = view.findViewById(R.id.playlistImage)
        private val name: TextView = view.findViewById(R.id.playlistName)
        private val creator: TextView = view.findViewById(R.id.playlistCreator)
        private val nbTracks: TextView = view.findViewById(R.id.playlistNbTracks)

        fun onBind(playlist: Playlist) {
            Picasso.get().load(playlist.picture).into(picture)
            name.text = playlist.title
            creator.text = "${playlist.creator.name} ${playlist.creator.lastName}"
            nbTracks.text = "${playlist.nbTracks} tracks"
        }

        init {
            itemView.setOnClickListener {
                val oldSelected = selected
                selected = if(adapterPosition == selected) RecyclerView.NO_POSITION else adapterPosition

                notifyItemChanged(adapterPosition)
                notifyItemChanged(oldSelected)

                onSelection(selected)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PlaylistsViewHolder = PlaylistsViewHolder(LayoutInflater
        .from(parent.context)
        .inflate(R.layout.recycler_playlist, parent, false))


    override fun onBindViewHolder(holder: PlaylistsViewHolder, position: Int) {
        if (position == RecyclerView.NO_POSITION) return
        holder.onBind(playlists[position])
        holder.itemView.setBackgroundColor(if(selected==position) Color.GRAY else Color.WHITE)
    }
    override fun getItemCount() = playlists.size
}