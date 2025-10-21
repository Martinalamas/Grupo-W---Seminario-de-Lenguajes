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

    lateinit var recyclerView: RecyclerView
    lateinit var cancionAdapter: CancionAdapter
    lateinit var descargar: Button
    lateinit var toolbar: Toolbar

    val albumId = intent.getStringExtra("album_id") ?: "3i4nU0OIi7gMmXDEhG9ZRt"

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
        descargar = findViewById(R.id.btnDescargar)
        cancionAdapter = CancionAdapter(getCanciones(), this, albumId)
        recyclerView.adapter = cancionAdapter

        toolbar = findViewById(R.id.toolbarTitulo)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Mis canciones favoritas"

        toolbar.setNavigationOnClickListener {
            val intent = Intent(this, BienvenidaActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }

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
