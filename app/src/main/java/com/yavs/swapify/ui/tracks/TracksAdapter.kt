package com.yavs.swapify.ui.tracks

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.yavs.swapify.R
import com.yavs.swapify.data.model.Track


class TracksAdapter(
    private val tracks: List<Track>,
    private val onSelection: (pos: Int, selectionType: TracksFragment.SelectionType ) -> Unit
) : RecyclerView.Adapter<TracksAdapter.TrackViewHolder>() {

    private var selected = (tracks.indices).toMutableList()


    inner class TrackViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val name: TextView = view.findViewById(R.id.trackName)
        private val creator: TextView = view.findViewById(R.id.trackArtist)
        private val player: ImageButton = view.findViewById(R.id.playerButton)
        private val mediaPlayer = MediaPlayer()

        fun onBind(track: Track) {
            player.setImageDrawable(
                ContextCompat.getDrawable(
                    itemView.context,
                    if (mediaPlayer.isPlaying) R.drawable.baseline_pause_24 else R.drawable.baseline_play_arrow_24
                )
            )

            try {
                mediaPlayer.setAudioAttributes(AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build())
                mediaPlayer.setDataSource(track.preview)
                mediaPlayer.prepareAsync()
                mediaPlayer.setOnPreparedListener{ player.isEnabled = true }
            } catch (_: Exception) {
                mediaPlayer.reset()
            }


            player.setOnClickListener{
                if (mediaPlayer.isPlaying) {
                    player.setImageDrawable(ContextCompat.getDrawable(itemView.context, R.drawable.baseline_play_arrow_24))
                    mediaPlayer.pause()
                }
                else{
                    player.setImageDrawable(ContextCompat.getDrawable(itemView.context,R.drawable.baseline_pause_24))
                    mediaPlayer.start()
                }
            }

            itemView.setOnClickListener {
                if(selected.removeIf{ it == adapterPosition })
                    onSelection(adapterPosition, TracksFragment.SelectionType.DESELECTION)
                else{
                    selected.add(adapterPosition)
                    onSelection(adapterPosition, TracksFragment.SelectionType.SELECTION)
                }
                mediaPlayer.reset()
                notifyItemChanged(adapterPosition)
            }

            name.text = if(track.title != null && track.title.length > 30) "${track.title.substring(0, 30)}..." else track.title
            creator.text = if(track.artistName != null && track.artistName.length > 30) "${track.artistName.substring(0, 30)}..." + "..." else track.artistName
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
            ContextCompat.getColor(
                holder.itemView.context,
                if(selected.any { it == position }) R.color.secondary_bg_color else R.color.main_bg_color
            )
        )
    }
    override fun getItemCount() = tracks.size
}