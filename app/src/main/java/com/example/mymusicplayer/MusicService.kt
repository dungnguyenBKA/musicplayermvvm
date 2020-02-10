package com.example.mymusicplayer

import android.content.Context
import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import com.example.mymusicplayer.model.Song

class MusicService private constructor(var context: Context): IMusicService {

    private var mediaPlayer: MediaPlayer?= null
    private var callback: MusicServiceCallback?= null

    private val handler : Handler = Handler(Looper.getMainLooper())

    private var runnable = object : Runnable{
        override fun run() {
            callback?.onSetProcess(mediaPlayer!!.currentPosition)

            handler.postDelayed(this, 1000)
        }
    }

    override fun play(song: Song) {
        if(mediaPlayer != null){
            mediaPlayer?.release()
            mediaPlayer = null
        }

        mediaPlayer = MediaPlayer.create(context, song.uri).apply {
            start()

            setOnCompletionListener {
                mediaPlayer?.release()
                handler.removeCallbacks(runnable)
            }
        }

        handler.postDelayed(runnable, 1000)

        callback?.onSetDuration(mediaPlayer!!.duration)
    }

    override fun resume() {
        if( mediaPlayer!=null && !mediaPlayer!!.isPlaying){
            mediaPlayer!!.start()
        }
    }

    override fun pause() {
        if( mediaPlayer!=null && mediaPlayer!!.isPlaying){
            mediaPlayer!!.pause()
        }
    }

    override fun seek(seekValue: Int) {
        if( mediaPlayer!=null){
            mediaPlayer?.apply {
                seekTo(seekValue)
            }
        }
    }

    fun setCallback(mCallBack: MusicServiceCallback){
        this.callback = mCallBack
    }

    interface MusicServiceCallback{
        fun onSetDuration(duration: Int)
        fun onSetProcess(process: Int)
    }

    companion object{
        private var INSTANCE: MusicService?= null

        fun getInstance(context: Context) = INSTANCE?: MusicService(context).also { INSTANCE = it }
    }
}