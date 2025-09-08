package com.example.mastroberti_tp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat




private lateinit var BotonRegistrate: Button
private lateinit var BotonInicioSesion: Button


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets


        }


        // Inicializa las variables de los botones
        BotonRegistrate = findViewById(R.id.BotonRegistrate)
        BotonInicioSesion = findViewById(R.id.BotonInicioSesion)


        // Acción Boton Registrate
        BotonRegistrate.setOnClickListener {
            //Para "conectar" con la pantalla de terminos y condiciones.
            val intent = Intent(this, TerminosYCondiciones::class.java)
            startActivity(intent)
        }


        // Acción Boton Inicia Sesion
        BotonInicioSesion.setOnClickListener {
            // Crea el Intent para ir a InicioSesion_Activity
            // 'this' se refiere al contexto de MainActivity
            val intent = Intent(this, InicioSesion_Activity::class.java)

            startActivity(intent)


        }
    }
}


