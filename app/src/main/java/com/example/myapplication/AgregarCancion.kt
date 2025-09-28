package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AgregarCancion : AppCompatActivity() {

    //Vinculacion de variables
    lateinit var tituloCancion : EditText
    lateinit var artista : EditText
    lateinit var agregar : Button
    lateinit var toolbar : Toolbar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_agregar_cancion)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Vinculacion de variables con su ID
        tituloCancion = findViewById(R.id.idCancion)
        artista = findViewById(R.id.idArtista)
        agregar = findViewById(R.id.btnAgregar)
        toolbar = findViewById(R.id.toolbarAtras)

        //Establecimiento de la toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toolbar.setNavigationOnClickListener {
            val intent = Intent(this, BienvenidaActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()

        }


        //Al dar click en el boton de agregar, se agrega la cancion a la base de datos y se muestra la actividad Playlist
        agregar.setOnClickListener {
            //Si los campos no estan llenos, se muestra un mensaje de error
            if (tituloCancion.text.isEmpty() && artista.text.isEmpty()) {
                tituloCancion.error = "El titulo debe estar completo"
                artista.error = "El artista debe estar completo"
                Toast.makeText(this, "Ingrese los datos", Toast.LENGTH_SHORT).show()
            } else {
                var nuevaCancion = Cancion( tituloCancion.text.toString(), 0, artista.text.toString(), "00:00")
                AppDataBase.getDatabase(applicationContext).cancionDao().insertAll(nuevaCancion)
                val intent = Intent(this, PlaylistActivity::class.java)
                startActivity(intent)
            }

        }






    }



}