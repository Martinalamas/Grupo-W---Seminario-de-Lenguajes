package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AgregarCancion : AppCompatActivity() {

    lateinit var tituloCancion : EditText
    lateinit var artista : EditText
    lateinit var agregar : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_agregar_cancion)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        tituloCancion = findViewById(R.id.idCancion)
        artista = findViewById(R.id.idArtista)
        agregar = findViewById(R.id.btnAgregar)

        agregar.setOnClickListener {
            if (tituloCancion.text.isEmpty() && artista.text.isEmpty()) {
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