package com.example.mymusicplayer.view

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.mymusicplayer.MusicService
import com.example.mymusicplayer.R
import com.example.mymusicplayer.modelview.MusicViewModel

class MainActivity : AppCompatActivity(){

    private val REQUEST_CODE = 123

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        // check permission storage
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED){
            createPlayer()
        }
        else{
            // ask for permission
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_CODE)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode == REQUEST_CODE){
            if(grantResults[0] != PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Sorry, app cannot work without this permission", Toast.LENGTH_SHORT).show()
                finish()
            }
            else{
                createPlayer()
            }
        }
    }

    private fun createPlayer() {
        val listSongFragment = ListSongFragment.newInstance()

        supportFragmentManager.beginTransaction()
            .replace(R.id.container_view, listSongFragment)
            .commit()
    }
}
