package com.example.myapplication

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class activity_fragmentos : AppCompatActivity(), PrimerFragmentoInterfaz {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val primerFragmento = supportFragmentManager
            .findFragmentById(R.id.contenedor_primer_fragmento) as? PrimerFragmento
        primerFragmento?.listener = this

    }

    override fun mostrarContenido() {
        val segundoFragmento = SegundoFragmento()
        supportFragmentManager.beginTransaction()
            .replace(R.id.contenedor_segundo_fragmento, segundoFragmento)
            .addToBackStack(null)
            .commit()

    }
}