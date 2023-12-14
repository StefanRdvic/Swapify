package com.yavs.swapify.ui.tracks

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso
import com.yavs.swapify.R
import com.yavs.swapify.data.model.Track


class TracksAdapter(
    private val tracks: MutableList<Track>,
    private val colorSelector: (id: Int) -> Int,
    private val onSelection: (pos: MutableList<Int>) -> Unit
) : RecyclerView.Adapter<TracksAdapter.TrackViewHolder>() {

    private var selected = (0..tracks.size).toMutableList()

    inner class TrackViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val picture: ImageView = view.findViewById(R.id.trackImage)
        private val name: TextView = view.findViewById(R.id.trackName)
        private val creator: TextView = view.findViewById(R.id.trackArtist)
        private val player: FloatingActionButton = view.findViewById(R.id.fabPlayOrPause)
        private val mediaPlayer = MediaPlayer()

        init {
            itemView.setOnClickListener {
                if( selected.any{ it == adapterPosition} ) selected.removeIf{it==adapterPosition} else selected.add(adapterPosition)
                notifyItemChanged(adapterPosition)
                Log.i("ok",selected.toString())
                onSelection(selected)
            }
            mediaPlayer.setAudioAttributes(AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build())
        }

        fun onBind(track: Track) {
            if(track.image.isNullOrBlank()){
                Picasso.get().load(track.image).into(picture)
            }
            try {
                mediaPlayer.setDataSource(track.preview)
                mediaPlayer.prepareAsync()
                mediaPlayer.setOnPreparedListener{ onPrepared() }
                mediaPlayer.setOnCompletionListener { player.setImageDrawable(ContextCompat.getDrawable(itemView.context,R.drawable.baseline_play_arrow_24)) }
            } catch (_: Exception) {
                mediaPlayer.reset()
            }
            player.setOnClickListener{
                Toast.makeText(itemView.context,"no preview for ${track.title}", Toast.LENGTH_SHORT).show()
            }

            name.text = track.title
            creator.text = track.artistName
        }

        private fun onPrepared() {
            player.show()
            player.setOnClickListener{
                if (mediaPlayer.isPlaying){
                    player.setImageDrawable(ContextCompat.getDrawable(itemView.context,R.drawable.baseline_play_arrow_24))
                    mediaPlayer.pause()
                }else{
                    player.setImageDrawable(ContextCompat.getDrawable(itemView.context,R.drawable.baseline_pause_24))
                    mediaPlayer.start()
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TrackViewHolder = TrackViewHolder(
        LayoutInflater
            .from(parent.context)
            .inflate(R.layout.recycler_track, parent, false))


    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        if (position == RecyclerView.NO_POSITION) return
        holder.onBind(track = tracks[position])
        holder.itemView.setBackgroundColor(
            if(selected.any{it==position}) colorSelector(R.color.secondary_bg_color)else colorSelector(R.color.main_bg_color)
        )
    }
    override fun getItemCount() = tracks.size
}