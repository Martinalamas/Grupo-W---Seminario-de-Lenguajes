package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class TerminosYCondiciones : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        //Declaración de variables
        lateinit var btnAcepto : Button
        lateinit var imagen : ImageView
        lateinit var btnCheck : CheckBox

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_terminos_ycondiciones)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Conectar variables con ID
        btnAcepto = findViewById(R.id.idButton)
        imagen = findViewById(R.id.idImagen)
        btnCheck = findViewById(R.id.idCheck)

        //Evento click imagen
        imagen.setOnClickListener {
            Toast.makeText(this, "Logo Spotify", Toast.LENGTH_SHORT).show()
        }


        //Evento click botón aceptar
        btnAcepto.setOnClickListener {
            Toast.makeText(this, "Boton aceptar", Toast.LENGTH_SHORT).show()
            //Verificar si el checkbox está marcado
            if (btnCheck.isChecked) {
                //Si esta marcado, se abre la actividad Registro
                val intent = Intent(this, Registro::class.java)
                startActivity(intent)
                finish()
            } else {
                //Si no esta marcado, se muestra un mensaje de error
                Toast.makeText(this, "Debe aceptar los términos y condiciones para avanzar", Toast.LENGTH_SHORT).show()
            }
        }
    }
}