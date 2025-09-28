package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class InicioSesion_Activity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        // Declara las variables para las vistas
        lateinit var nombreUsuarioEditText: EditText
        lateinit var contrasenaEditText: EditText
        lateinit var verificacionCheckBox: CheckBox
        lateinit var botonInicioSesion: Button


        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_inicio_sesion)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializa las vistas usando findViewById
        nombreUsuarioEditText = findViewById(R.id.NombreUsuario)
        contrasenaEditText = findViewById(R.id.Contrasena)
        verificacionCheckBox = findViewById(R.id.Verificacion)
        botonInicioSesion = findViewById(R.id.BotonInicioSesion)


        // Configurar el listener para el botón de Iniciar Sesión
        botonInicioSesion.setOnClickListener {

            val nombreUsuario = nombreUsuarioEditText.text.toString()
            val contrasena = contrasenaEditText.text.toString()
            val recordarUsuario = verificacionCheckBox.isChecked


            //  Lógica de Validación (Ejemplo Básico)
            if (nombreUsuario.isEmpty()) {
                nombreUsuarioEditText.error = "El nombre de usuario no puede estar vacío"
                return@setOnClickListener // Sale del listener si hay error
            }


            if (contrasena.isEmpty()) {
                contrasenaEditText.error = "La contraseña no puede estar vacía"
                return@setOnClickListener // Sale del listener si hay error
            }


            //Inicio de sesion.


            if (!nombreUsuario.isEmpty() && !contrasena.isEmpty()) {
                Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_LONG).show()


                if (recordarUsuario) {
                    Toast.makeText(this, "Se recordará el usuario.", Toast.LENGTH_SHORT).show()


                }
                val intent = Intent(this, Top10Activity::class.java)
                startActivity(intent)
                finish()






            }
        }


        // hacer algo cuando cambia el estado del CheckBox "Verificacion"
        verificacionCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                Toast.makeText(this, "Se recordará el usuario", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "No se recordará el usuario", Toast.LENGTH_SHORT).show()
            }
        }
    }




}
