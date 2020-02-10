package com.example.mymusicplayer.model

import android.content.ContentUris
import android.net.Uri
import android.provider.MediaStore

data class Song(
    var id: Long,
    var title: String,
    var artist: String
){
    var uri : Uri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, this.id)
}