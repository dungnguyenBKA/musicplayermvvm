package com.example.mymusicplayer.adapter

import android.content.ContentUris
import android.graphics.BitmapFactory
import android.os.Build
import android.provider.MediaStore
import android.util.Size
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mymusicplayer.R
import com.example.mymusicplayer.model.Song
import kotlinx.android.synthetic.main.item_song.view.*

class Adapter: RecyclerView.Adapter<Adapter.ViewHolder>() {
    private var listSong = ArrayList<Song>()
    private var listener: OnSongClickListener?=null

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val songTitle: TextView = itemView.song_title
        val songArtist: TextView = itemView.song_artist
        val songImage: ImageView = itemView.song_image
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_song, parent, false))
    }

    override fun getItemCount(): Int {
        return listSong.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.songArtist.text = listSong[position].artist
        holder.songTitle.text = listSong[position].title

        holder.itemView.setOnClickListener{
            listener?.onSongClick(position)
        }
    }

    fun updateList(listSong : ArrayList<Song>?){
        if(listSong != null){
            this.listSong = listSong
            notifyDataSetChanged()
        }
    }

    fun setOnSongClickListener(mOnSongClickListener: OnSongClickListener){
        this.listener = mOnSongClickListener
    }

    @FunctionalInterface
    interface OnSongClickListener{
        fun onSongClick(index: Int)
    }
}