package com.example.mymusicplayer.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.mymusicplayer.R
import com.example.mymusicplayer.adapter.Adapter
import com.example.mymusicplayer.model.Song
import com.example.mymusicplayer.modelview.MusicViewModel
import kotlinx.android.synthetic.main.item_song.*
import kotlinx.android.synthetic.main.list_song.*

class ListSongFragment : Fragment(), Adapter.OnSongClickListener{

    private val adapter = Adapter()
    private lateinit var model: MusicViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.list_song, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // create an instance of modelView
        model = ViewModelProviders.of(requireActivity()).get(MusicViewModel::class.java)
        model.init(this.requireContext())
        adapter.setOnSongClickListener(this)

        initListView()
        remote()

        if(model.songList.value.isNullOrEmpty()) {
            // need to scan new data
            model.getAllSong()
        }

        seek_bar.progress = model.songProcess.value?: 0
        seek_bar.max = model.songDuration.value?: 0
        song_name.text = model.songCurrent.value?.title
        artist_name.text = model.songCurrent.value?.artist

        println(model)

        model.songList.observe(this,
            Observer{
                adapter.updateList(it)
                println("changed: $it")
            })

        model.songProcess.observe(this,
            Observer {
                seek_bar.progress = it
            })

        model.songDuration.observe(this,
            Observer {
                seek_bar.max = it
                println("max seek = $it")
            })

        model.songCurrent.observe(this,
            Observer {
                song_name.text = it.title
                artist_name.text = it.artist
            })

        seek_bar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {}

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                model.seek(seekBar?.progress?:0)
            }
        })
    }

    private fun remote() {
        resume_pause.setOnClickListener{

        }

        play_layout.setOnClickListener{
            val detailSongFragment = DetailSongFragment.newInstance()

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.container_view, detailSongFragment)
                .addToBackStack(null)
                .commit()
        }
    }

    companion object {
        fun newInstance() = ListSongFragment()
    }

    private fun initListView(){
        adapter.updateList(model.songList.value)
        list_song.adapter = adapter
    }

    override fun onSongClick(index: Int) {
        val song = model.songList.value?.get(index)
        model.songCurrent.postValue(song)
        model.play(song!!)
    }
}
