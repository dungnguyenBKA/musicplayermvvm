package com.example.mymusicplayer.modelview

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mymusicplayer.MusicService
import com.example.mymusicplayer.model.Song
import com.example.mymusicplayer.repository.Repo

class MusicViewModel : ViewModel(), Repo.OnRepoCallBack, MusicService.MusicServiceCallback{
    /* live data */
    var songList: MutableLiveData<ArrayList<Song>> = MutableLiveData()
    var songProcess : MutableLiveData<Int> = MutableLiveData()
    var songDuration: MutableLiveData<Int> = MutableLiveData()
    var songCurrent: MutableLiveData<Song> = MutableLiveData()

    private var repo : Repo? = null
    private var context: Context? = null
    private var musicService: MusicService? = null


    fun init(context: Context){
        if(this.context == null){
            this.context = context
            repo = Repo.getInstance(context)
            musicService = MusicService.getInstance(context)
        }
        repo?.setOnRepoCallBackListener(this)
        musicService?.setCallback(this)
    }

    fun getAllSong(){
        repo?.scanAllSongInDevice()
    }

    override fun getAllSongCallback(listSong: ArrayList<Song>) {
        songList.postValue(listSong)
    }

    override fun onCleared() {
        println("ModelView cleared")
        super.onCleared()
    }

    /*
    callback of fun play in MusicService
    */
    override fun onSetDuration(duration: Int) {
        songDuration.postValue(duration)
    }

    override fun onSetProcess(process: Int) {
        songProcess.postValue(process)
    }

    fun play(song: Song){
        musicService?.play(song)
    }

    fun seek(seekValue: Int){
        musicService?.seek(seekValue)
    }
}