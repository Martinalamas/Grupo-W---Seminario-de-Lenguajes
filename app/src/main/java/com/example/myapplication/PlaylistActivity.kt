package com.example.myapplication

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView

class PlaylistActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var cancionAdapter: CancionAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_playlist)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        recyclerView = findViewById(R.id.recyclerView)
        cancionAdapter = CancionAdapter(getCanciones(), this)
        recyclerView.adapter = cancionAdapter

    }

    private fun getCanciones(): MutableList<Cancion>{
        var canciones: MutableList<Cancion> = ArrayList()
        var bdd= AppDataBase.getDatabase(applicationContext)
        canciones.addAll(bdd.cancionDao().getAll())
        return canciones
    }
}