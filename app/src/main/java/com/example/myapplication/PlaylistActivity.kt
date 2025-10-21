package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView

class PlaylistActivity : AppCompatActivity() {

    //Inicialización de variables
    lateinit var recyclerView: RecyclerView
    lateinit var cancionAdapter: CancionAdapter
    lateinit var descargar: Button
    lateinit var toolbar: Toolbar

    // AlbumId se obtiene desde el intent o es null si no se pasa nada
    private val albumId: String? by lazy {
        intent?.getStringExtra("album_id")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_playlist)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Conecto las variables
        recyclerView = findViewById(R.id.recyclerView)
        descargar = findViewById(R.id.btnDescargar)

        // Inicializo el Adapter pasando albumId como nulo
        cancionAdapter = CancionAdapter(getCanciones(), this, albumId)
        recyclerView.adapter = cancionAdapter

        //Toolbar
        toolbar = findViewById(R.id.toolbarTitulo)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Mis canciones favoritas"

        //Si se toca la flecha, se vuelve a la pantalla anterior
        toolbar.setNavigationOnClickListener {
            val intent = Intent(this, BienvenidaActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }

        //Al dar click en el botón descargar, se pasa a la pantalla para descargar las canciones
        descargar.setOnClickListener {
            val intent = Intent(this, DescargaYEscuchaActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getCanciones(): MutableList<Cancion> {
        val canciones: MutableList<Cancion> = ArrayList()
        val bdd = AppDataBase.getDatabase(applicationContext)
        canciones.addAll(bdd.cancionDao().getAll())
        return canciones
    }
}
