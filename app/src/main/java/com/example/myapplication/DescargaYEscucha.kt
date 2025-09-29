package com.example.myapplication

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DescargaYEscuchaActivity : AppCompatActivity() {

    lateinit var musicaDescarga: TextView
    lateinit var musicaSonando: TextView
    lateinit var botonSonar: Button
    lateinit var botonDescargar: Button

    var numeroCancion = 1
    var numeroDescarga = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_descargayescucha)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        musicaSonando = findViewById(R.id.musicaSonando)
        musicaDescarga = findViewById(R.id.musicaDescarga)
        botonSonar = findViewById(R.id.botonSonar)
        botonDescargar = findViewById(R.id.botonDescargar)

        botonSonar.setOnClickListener {
            musicaSonando.setText("Suena la cancion numero $numeroCancion de tu Playlist")
            numeroCancion++
        }

        botonDescargar.setOnClickListener {
            musicaDescarga.setText("Descargando cancion numero $numeroDescarga ¡Próximamente podrás escucharla sin conexión!")
            simularDescargaConCorrutinas(numeroDescarga)
            numeroDescarga++
        }
    }

    private fun simularDescargaConCorrutinas(numeroDescarga: Int) {
        // La corrutina se lanza en el contexto del ciclo de vida de la Activity
        lifecycleScope.launch {
            // Se cambia al hilo de I/O para la tarea pesada y no bloquear la UI
            withContext(Dispatchers.IO) {
                Thread.sleep(2000)
            }
            // Se vuelve automáticamente al hilo principal para actualizar la UI
            musicaDescarga.setText("Descarga numero $numeroDescarga finalizada")
        }
    }
}