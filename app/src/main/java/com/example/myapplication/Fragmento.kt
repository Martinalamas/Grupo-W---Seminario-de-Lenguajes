package com.example.myapplication

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Fragmento : AppCompatActivity(), PrimerFragmentoInterfaz {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragmento)

        if (savedInstanceState == null) {
            val primerFragmento = PrimerFragmento()
            supportFragmentManager.beginTransaction()
                .replace(R.id.contenedor_primer_fragmento, primerFragmento)
                .commit()
            primerFragmento.listener = this
        } else {
            val primerFragmento =
                supportFragmentManager.findFragmentById(R.id.contenedor_primer_fragmento) as? PrimerFragmento
            primerFragmento?.listener = this
        }
    }

    override fun mostrarContenido() {
        val segundoFragmento = SegundoFragmento()
        supportFragmentManager.beginTransaction()
            .replace(R.id.contenedor_segundo_fragmento, segundoFragmento)
            .addToBackStack(null)
            .commit()
    }
}