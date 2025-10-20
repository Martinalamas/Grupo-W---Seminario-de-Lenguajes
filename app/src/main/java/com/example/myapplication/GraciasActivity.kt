package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
// Importación correcta del Fragmento, ya que ambos están en el mismo paquete
import com.example.myapplication.PrimerFragmento
// NECESITAS ESTA IMPORTACIÓN PARA LA ACTIVITY DEL TOP 10
import com.example.myapplication.Top10Activity // AJUSTA ESTE PAQUETE SI ES DIFERENTE

class GraciasActivity : AppCompatActivity() {

    // Declaración de los botones
    private lateinit var botonVolverTop10 : Button
    private lateinit var botonCalificanos : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_gracias)

        // Manejo de insets (código que tenías)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // ===============================================
        // LÓGICA DEL BOTÓN "VOLVER AL TOP 10" (Activity -> Activity)
        // ===============================================
        botonVolverTop10 = findViewById(R.id.botonVolverTop10)
        botonVolverTop10.setOnClickListener {
            val intent = Intent(this, Top10Activity::class.java)
            startActivity(intent)
        }

        // ===============================================
        // LÓGICA DEL BOTÓN "CALIFICANOS" (Activity -> Fragmento 1)
        // ===============================================

        // 1. Enlazar el botón "Calificanos" con su ID en el XML
        botonCalificanos = findViewById(R.id.botonCalificanos)

        // 2. Asignar el listener para cargar el PrimerFragmento
        botonCalificanos.setOnClickListener {
            // Usamos supportFragmentManager para manejar los Fragmentos
            supportFragmentManager.beginTransaction()
                // Carga el PRIMER FRAGMENTO en el contenedor (R.id.fragment_container)
                .replace(R.id.fragment_container, PrimerFragmento())
                // Añadimos a la pila para poder volver a GraciasActivity al presionar 'Atrás'
                .addToBackStack(null)
                .commit()
        }
    }
}