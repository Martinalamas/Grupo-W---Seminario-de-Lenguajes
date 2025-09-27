package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.top10Act)) { v, insets ->
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId=R.id.item_top10){
            val intent = intent(this,rvTop10Activity::class.java)
            startActivity(intent)
        }

       if (item.itemId=R.id.item_logout){
           val intent = intent(this,InicioSesion_Activity::class.java)
           startActivity(intent)
       }

        return super.onOptionsItemSelected(item)
    }
}

private fun getCanciones(): MutableList<Cancion>{
    var canciones: MutableList<Cancion> = ArrayList()
    canciones.add(Cancion(titulo = "HOLA PERDIDA",1, artista = "Luck Ra & Khea", duracion = "3:12"))
    canciones.add(Cancion(titulo = "Piel",2, artista = "Tiago PZK & Ke Personaje", duracion = "2:58"))
    canciones.add(Cancion(titulo = "LUNA",3, artista = "Feid & ATL Jacob", duracion = "3:45"))
    canciones.add(Cancion(titulo = "Que me falte todo",4, artista = "Luck Ra & Abel Pintos", duracion = "3:20"))
    canciones.add(Cancion(titulo = "Tu Misterioso Alguien",5, artista = "Miranda!", duracion = "4:04"))
    canciones.add(Cancion(titulo = "7 VIDAS",6, artista = "Maria Becerra", duracion = "2:24"))
    canciones.add(Cancion(titulo = "La_Original.mp3",7, artista = "Emilia & TINI", duracion = "2:20"))
    canciones.add(Cancion(titulo = "Como Eran Las Cosas",8, artista = "Babasonicos", duracion = "4:00"))
    canciones.add(Cancion(titulo = "Baile Inolvidable",9, artista = "Bad Bunny", duracion = "6:07"))
    canciones.add(Cancion(titulo = "Amor de Vago",10, artista = "La T y La M, Malandro", duracion = "3:08'"))
    return canciones
}