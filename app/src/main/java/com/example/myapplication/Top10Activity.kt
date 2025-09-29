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
    lateinit var botonContinuar: AppCompatButton
    lateinit var botonDescarga: AppCompatButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_top10)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.rvTop10)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        botonContinuar = findViewById(R.id.botonContinuar)
        botonDescarga = findViewById(R.id.botonDescarga)

        botonContinuar.setOnClickListener {
            val intent = Intent(this, GraciasActivity::class.java)
            startActivity(intent)
        }

        botonDescarga.setOnClickListener {
            val intent = Intent(this, DescargaYEscuchaActivity::class.java)
            startActivity(intent)
        }

        toolbar = findViewById(R.id.toolbarTop10)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Top 10"

        rvTop10Activity = findViewById(R.id.rvTop10)
        rvTop10Activity.layoutManager = LinearLayoutManager(this)

        cancionesAdapter = CancionAdapter(getCanciones(), this)
        rvTop10Activity.adapter = cancionesAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_playlist -> {
                startActivity(Intent(this, PlaylistActivity::class.java))
            }
            R.id.item_logout -> {
                startActivity(Intent(this, InicioSesion_Activity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getCanciones(): MutableList<Cancion> {
        return mutableListOf(
            Cancion(titulo = "HOLA PERDIDA", top = 1, artista = "Luck Ra & Khea", duracion = "3:12"),
            Cancion(titulo = "Piel", top = 2, artista = "Tiago PZK & Ke Personaje", duracion = "2:58"),
            Cancion(titulo = "LUNA", top = 3, artista = "Feid & ATL Jacob", duracion = "3:45"),
            Cancion(titulo = "Que me falte todo", top = 4, artista = "Luck Ra & Abel Pintos", duracion = "3:20"),
            Cancion(titulo = "Tu Misterioso Alguien", top = 5, artista = "Miranda!", duracion = "4:04"),
            Cancion(titulo = "7 VIDAS", top = 6, artista = "Maria Becerra", duracion = "2:24"),
            Cancion(titulo = "La_Original.mp3", top = 7, artista = "Emilia & TINI", duracion = "2:20"),
            Cancion(titulo = "Como Eran Las Cosas", top = 8, artista = "Babasonicos", duracion = "4:00"),
            Cancion(titulo = "Baile Inolvidable", top = 9, artista = "Bad Bunny", duracion = "6:07"),
            Cancion(titulo = "Amor de Vago", top = 10, artista = "La T y La M, Malandro", duracion = "3:08")
        )
    }
}
