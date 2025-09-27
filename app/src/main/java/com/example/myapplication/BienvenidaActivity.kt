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

    lateinit var titulo : TextView
    lateinit var boton : Button
    lateinit var subtitulo : TextView
    lateinit var botonAtras : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_bienvenida)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        titulo = findViewById(R.id.idTitulo)
        boton = findViewById(R.id.btnTop)
        subtitulo = findViewById(R.id.idSub)
        botonAtras = findViewById(R.id.btnAtras)

        val nombre = intent.getStringExtra(resources.getString(R.string.nombre))

        titulo.text = "¡Bienvenido $nombre!"

        boton.setOnClickListener {
            val intent = Intent(this, Top10Activity::class.java)
            startActivity(intent)
            true
        }

        botonAtras.setOnClickListener {
           val intent = Intent(this, AgregarCancion::class.java)
            startActivity(intent)
            true
        }

    }
}