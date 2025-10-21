package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.squareup.picasso.Picasso

class DetalleCancionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detalle_cancion)

        val titulo = intent.getStringExtra("titulo")
        val artista = intent.getStringExtra("artista")
        val duracion = intent.getStringExtra("duracion")
        val imagen = intent.getStringExtra("imagen")

        findViewById<TextView>(R.id.DNombreCancion).text = titulo
        findViewById<TextView>(R.id.DArtista).text = artista
        findViewById<TextView>(R.id.dc_duracion).text = duracion

        val ivPortada = findViewById<ImageView>(R.id.iv_portada)

            if (!imagen.isNullOrEmpty()){
                Picasso.get().load(imagen).into(ivPortada)
            }

        lateinit var botonVolver : AppCompatButton


        botonVolver = findViewById<AppCompatButton>(R.id.botonVolver)

        botonVolver.setOnClickListener {
            val intent = Intent(this, Top10Activity::class.java)
            startActivity(intent)
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}