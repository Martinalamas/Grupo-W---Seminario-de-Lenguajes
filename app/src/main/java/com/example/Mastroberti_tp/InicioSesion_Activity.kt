package com.example.mastroberti_tp

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class InicioSesion_Activity : AppCompatActivity() {


    // Declara las variables para las vistas
    private lateinit var nombreUsuarioEditText: EditText
    private lateinit var contrasenaEditText: EditText
    private lateinit var verificacionCheckBox: CheckBox
    private lateinit var botonInicioSesion: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio_sesion) // Asegúrate de que este nombre de layout sea correcto




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


            if (nombreUsuario == "usuario" && contrasena == "password") {
                Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_LONG).show()


                if (recordarUsuario) {
                    Toast.makeText(this, "Se recordará el usuario.", Toast.LENGTH_SHORT).show()


                }






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



