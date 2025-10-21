package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView

class PlaylistActivity : AppCompatActivity() {

    //Declaracion de variables
    lateinit var recyclerView: RecyclerView
    lateinit var cancionAdapter: CancionAdapter

    lateinit var descargar : Button

    lateinit var toolbar : Toolbar

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

        //Vinculacion de las variables con su ID
        recyclerView = findViewById(R.id.recyclerView)
        descargar = findViewById(R.id.btnDescargar)
        cancionAdapter = CancionAdapter(getCanciones(), this,albumId)
        recyclerView.adapter = cancionAdapter

        //Establecimiento de la toolbar
        toolbar = findViewById(R.id.toolbarTitulo)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Mis canciones favoritas"



        //Al dar click en la flecha, vuelve a la activity anterior
        toolbar.setNavigationOnClickListener {
            val intent = Intent(this, BienvenidaActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }



        //Al dar click en el boton de descargar, se muestra la actividad DescargaYEscucha
        ////descargar.setOnClickListener {
        //    val intent = Intent(this, DescargaYEscucha::class.java)
        //    startActivity(intent)
        //}




    }

    //Funcion para obtener las canciones de la base de datos
    private fun getCanciones(): MutableList<Cancion>{
        var canciones: MutableList<Cancion> = ArrayList()
        var bdd= AppDataBase.getDatabase(applicationContext)
        canciones.addAll(bdd.cancionDao().getAll())
        return canciones
    }
}