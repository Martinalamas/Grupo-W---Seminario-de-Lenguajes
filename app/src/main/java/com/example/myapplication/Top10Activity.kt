package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Top10Activity : AppCompatActivity() {

    lateinit var toolbar: Toolbar
    lateinit var rvTop10Activity: RecyclerView
    lateinit var cancionesAdapter: CancionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_top10)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val botonContinuar = findViewById<AppCompatButton>(R.id.botonContinuar)
        botonContinuar.setOnClickListener {
            val intent = Intent(this, GraciasActivity::class.java)
            startActivity(intent)
        }

        toolbar = findViewById(R.id.toolbarTop10)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Top 10"

        rvTop10Activity = findViewById(R.id.rvTop10)
        val lm = LinearLayoutManager(this)
        rvTop10Activity.layoutManager = lm


        cancionesAdapter = CancionAdapter(getCanciones(), this)
        rvTop10Activity.adapter = cancionesAdapter
    }
}

private fun getCanciones(): MutableList<Cancion>{
    var canciones: MutableList<Cancion> = ArrayList()
    canciones.add(Cancion(titulo = "HOLA PERDIDA",1, artista = "Luck Ra & Khea"))
    canciones.add(Cancion(titulo = "Piel",2, artista = "Tiago PZK & Ke Personaje"))
    canciones.add(Cancion(titulo = "LUNA",3, artista = "Feid & ATL Jacob"))
    canciones.add(Cancion(titulo = "Que me falte todo",4, artista = "Luck Ra & Abel Pintos"))
    canciones.add(Cancion(titulo = "Tu Misterioso Alguien",5, artista = "Miranda!"))
    canciones.add(Cancion(titulo = "7 VIDAS",6, artista = "Maria Becerra"))
    canciones.add(Cancion(titulo = "La_Original.mp3",7, artista = "Emilia & TINI"))
    canciones.add(Cancion(titulo = "Como Eran Las Cosas",8, artista = "Babasonicos"))
    canciones.add(Cancion(titulo = "Baile Inolvidable",9, artista = "Bad Bunny"))
    canciones.add(Cancion(titulo = "Amor de Vago",10, artista = "La T y La M, Malandro"))
    return canciones
}