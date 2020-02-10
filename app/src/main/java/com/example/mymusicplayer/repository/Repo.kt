package com.example.mymusicplayer.repository

import android.content.Context
import android.provider.MediaStore
import com.example.mymusicplayer.model.Song

class Repo private constructor(var context: Context){

    private var mOnRepoCallBack: OnRepoCallBack? = null

    fun scanAllSongInDevice(){
        val songList = ArrayList<Song>()

        val cursor = context.contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            null,
            null,
            null,
            MediaStore.Audio.Media.DEFAULT_SORT_ORDER
        )

        if(cursor!=null && cursor.moveToFirst()){
            val indexId = cursor.getColumnIndex(MediaStore.Audio.Media._ID)
            val indexTitle = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)
            val indexArtist = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)

            do{
                songList.add(
                    Song(
                        cursor.getLong(indexId),
                        cursor.getString(indexTitle),
                        cursor.getString(indexArtist)
                    )
                )

            }while (cursor.moveToNext())
        }

        cursor?.close()
        // callback
        mOnRepoCallBack?.getAllSongCallback(songList)
    }

    fun setOnRepoCallBackListener(mOnRepoCallBack: OnRepoCallBack){
        this.mOnRepoCallBack = mOnRepoCallBack
    }

    interface OnRepoCallBack{
        fun getAllSongCallback(listSong: ArrayList<Song>)
    }

    companion object{
        private var repo: Repo? = null
        fun getInstance(context: Context) = repo?: Repo(context).also { repo = it }
    }
}