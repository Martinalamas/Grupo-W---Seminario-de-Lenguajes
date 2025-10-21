package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.content.Intent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ElegirAlbumActivity : AppCompatActivity() {

    lateinit var botonMiranda: Button
    lateinit var botonLali: Button
    lateinit var botonRedondos: Button
    lateinit var botonSoda: Button

    val albumMiranda = "4di9h6Igl1rbd0xtZfsyD6"
    val AlbumLali = "56BPpEYgadIVVXYLabO5Je"
    val albumRedondos = "0TmPRZoau8M0QIfnMgwrlI"
    val albumSoda = "3i4nU0OIi7gMmXDEhG9ZRt"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_elegir_album)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        botonMiranda = findViewById(R.id.albumMiranda)
        botonLali = findViewById(R.id.albumLali)
        botonRedondos = findViewById(R.id.albumRedondos)
        botonSoda = findViewById(R.id.albumSoda)

        botonMiranda.setOnClickListener {
            abrirAlbum(albumMiranda)
        }

        botonLali.setOnClickListener {
            abrirAlbum(AlbumLali)
        }

        botonRedondos.setOnClickListener {
            abrirAlbum(albumRedondos)
        }

        botonSoda.setOnClickListener {
            abrirAlbum(albumSoda)
        }
    }

    private fun abrirAlbum(albumId: String){
        val intent = Intent(this,Top10Activity::class.java)
        intent.putExtra("album_id",albumId)
        startActivity(intent)
    }

}
