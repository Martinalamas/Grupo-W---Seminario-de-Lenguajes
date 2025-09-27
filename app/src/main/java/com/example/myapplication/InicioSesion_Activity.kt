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

    // Declara las variables para las vistas
    lateinit var nombreUsuarioEditText: EditText
    lateinit var contrasenaEditText: EditText
    lateinit var verificacionCheckBox: CheckBox
    lateinit var botonInicioSesion: Button


    override fun onCreate(savedInstanceState: Bundle?) {



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

        // Recuperar las preferencias de inicio de sesión
        var preferencias = getSharedPreferences(resources.getString(R.string.sp_credenciales), MODE_PRIVATE)
        var nombreGuardado = preferencias.getString(resources.getString(R.string.nombre), "")
        var passwordGuardada = preferencias.getString(resources.getString(R.string.password), "")

        // Mostrar las preferencias si existen -> quedan escritas
        if (nombreGuardado!!.isNotEmpty() && passwordGuardada!!.isNotEmpty()) {
           nombreUsuarioEditText.setText(nombreGuardado)
            contrasenaEditText.setText(passwordGuardada)
            verificacionCheckBox.isChecked = true
        }


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


                // Si recordar inicio esta check, se guardan las preferencias
                if (recordarUsuario) {
                    login(nombreUsuario, contrasena)
                    Toast.makeText(this, "Se recordará el usuario.", Toast.LENGTH_SHORT).show()



                }
                iniciarBienvenida(nombreUsuario)







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

    //Funcion para iniciar la actividad Bienvenida
    private fun iniciarBienvenida (Usuario : String) {
        val intent = Intent(this, BienvenidaActivity::class.java)
        intent.putExtra(resources.getString(R.string.nombre), Usuario)
        startActivity(intent)
        finish()
    }

    //Funcion para guardar las preferencias de inicio de sesion
    private fun login(Usuario : String, Password : String) {
        var preferencias = getSharedPreferences(resources.getString(R.string.sp_credenciales), MODE_PRIVATE)

        preferencias.edit().apply {
            putString(resources.getString(R.string.nombre), Usuario)
            putString(resources.getString(R.string.password), Password)
            apply()
        }


    }






}
