package com.example.mymusicplayer.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import com.example.mymusicplayer.R
import com.example.mymusicplayer.modelview.MusicViewModel
import kotlinx.android.synthetic.main.detail_song.*

class DetailSongFragment : Fragment() {
    private var musicviewmodel : MusicViewModel?=null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        musicviewmodel = ViewModelProviders.of(context as FragmentActivity).get(MusicViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.detail_song, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        back.setOnClickListener{
            requireActivity().supportFragmentManager.popBackStack()
        }

        song_artist_detail.text = musicviewmodel?.songCurrent?.value?.artist ?: "Unknown"
        song_title_detail.text = musicviewmodel?.songCurrent?.value?.title ?: "Unknown"

        musicviewmodel?.songCurrent?.observe(this,
            Observer {
                song_artist_detail.text = it.artist
                song_title_detail.text = it.title
            })
    }

    companion object {
        fun newInstance() = DetailSongFragment()
    }
    //abc
}
