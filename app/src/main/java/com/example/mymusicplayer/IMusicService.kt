package com.example.mymusicplayer

import com.example.mymusicplayer.model.Song

interface IMusicService {
    fun play(song: Song)
    fun resume()
    fun pause()
    fun seek(seekValue: Int)
}