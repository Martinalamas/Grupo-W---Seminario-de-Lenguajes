package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.content.Intent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class BienvenidaActivity : AppCompatActivity() {

    //Declaracion de variables
    lateinit var titulo : TextView
    lateinit var boton : Button
    lateinit var subtitulo : TextView
    lateinit var botonAtras : Button

    lateinit var botonPlaylist : Button

    lateinit var botonAgregar : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_bienvenida)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Vinculacion de las variables con su ID
        titulo = findViewById(R.id.idTitulo)
        boton = findViewById(R.id.btnTop)
        subtitulo = findViewById(R.id.idSub)
        botonAtras = findViewById(R.id.btnAtras)
        botonPlaylist = findViewById(R.id.btnPlaylist)
        botonAgregar = findViewById(R.id.btnAgregar)

        //Obtencion del nombre de la Activity anterior
        val nombre = intent.getStringExtra(resources.getString(R.string.nombre))

        //Establecimiento del texto del titulo
        titulo.text = "¡Bienvenido $nombre!"

        //Al clickear en el boton, se muestra la actividad Top10
        boton.setOnClickListener {
            val intent = Intent(this, Top10Activity::class.java)
            startActivity(intent)
            true
        }

        //Al clickear en el boton de atras, se muestra la actividad Iniciar sesion
        botonAtras.setOnClickListener {
           val intent = Intent(this, InicioSesion_Activity::class.java)
            startActivity(intent)
            true
        }


        //Al clickear el boton agregar, se muestra la actividad AgregarCancion
        botonAgregar.setOnClickListener {
            val intent = Intent(this, AgregarCancion::class.java)
            startActivity(intent)
            true
        }

        //Al clickear el boton playlist, se muestra la actividad Playlist
        botonPlaylist.setOnClickListener {
            val intent = Intent(this, PlaylistActivity::class.java)
            startActivity(intent)
            true
        }

    }
}