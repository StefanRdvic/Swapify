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

class PlaylistAdapter(
    private val playlists: List<Playlist>,
    private val listener: (position: Int) -> Unit
) : RecyclerView.Adapter<PlaylistAdapter.PlaylistViewHolder>() {
    var selected = -1


    inner class PlaylistViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val picture: ImageView = view.findViewById(R.id.playlistImage)
        val name: TextView = view.findViewById(R.id.playlistName)
        val creator: TextView = view.findViewById(R.id.playlistCreator)
        val nbTracks: TextView = view.findViewById(R.id.playlistNbTracks)

        init {
            itemView.setOnClickListener {
                setSelection(adapterPosition)
                listener.invoke(adapterPosition)

            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_playlist, parent, false)

        return PlaylistViewHolder(view)
    }
    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        val playlist = playlists[position]
        Picasso.get().load(playlist.picture).into(holder.picture)
        holder.name.text = playlist.title
        holder.creator.text = "${playlist.creator.name} ${playlist.creator.lastName}"
        holder.nbTracks.text = "${playlist.nbTracks} tracks"

        if(selected==position){
            holder.itemView.setBackgroundColor(Color.GRAY)
        }else{
            holder.itemView.setBackgroundColor(Color.WHITE)
        }
    }
    override fun getItemCount() = playlists.size


    private fun setSelection(adapterPos: Int){
        if(adapterPos==RecyclerView.NO_POSITION) return
        notifyItemChanged(selected)
        selected=adapterPos
        notifyItemChanged(selected)
    }
}