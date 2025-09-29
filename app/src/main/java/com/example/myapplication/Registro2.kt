package com.example.myapplication

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.ContextCompat

class Registro2 : AppCompatActivity() {

    //Declaración de variables
    lateinit var contra : EditText
    lateinit var contra2: EditText
    lateinit var btnRegistrar : Button
    lateinit var toolbar: Toolbar
    lateinit var titulo : TextView
    lateinit var switchRecordarSesion: SwitchCompat
    // NUEVA VARIABLE PARA EL NOMBRE DE USUARIO
    lateinit var nombreUsuarioEditText: EditText

    // Hice la variable de SharedPreferences una propiedad de la clase
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro2)

        //Vinculacion de las varibles con su ID
        contra = findViewById(R.id.idContra)
        contra2 = findViewById(R.id.idContra2)
        btnRegistrar = findViewById(R.id.btnRegistrar)
        titulo = findViewById(R.id.idTitulo)
        toolbar = findViewById(R.id.toolbar)
        switchRecordarSesion = findViewById(R.id.idSwitch)
        // VINCULAMOS LA NUEVA VARIABLE
        nombreUsuarioEditText = findViewById(R.id.idNombreUsuario)

        //  Inicializo SharedPreferences
        sharedPreferences = getSharedPreferences(getString(R.string.sp_credenciales), MODE_PRIVATE)

        // Logica para cargar el estado guardado del usuario
        val estadoGuardado = sharedPreferences.getBoolean("recordar_sesion", false)
        switchRecordarSesion.isChecked = estadoGuardado

        //Establecimiento de la toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Registro"
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.spotify_green))

        //Al dar click, vuelve a Registro
        toolbar.setNavigationOnClickListener {
            val intent = Intent(this, Registro::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }

        //Recibe los datos del registro anterior
        val nombre = intent.getStringExtra("nombre")
        val apellido = intent.getStringExtra("apellido")

        //Muestro mensaje personalizado
        titulo.text = "¡Bienvenido $nombre $apellido!"

        //Al dar click, se validan los campos y si son validos, se muestra un mensaje de confirmacion
        btnRegistrar.setOnClickListener{
            val contraString = contra.text.toString()
            val contra2String = contra2.text.toString()

            if (contraString.isEmpty() || contra2String.isEmpty()) {
                contra.error = "Por favor, ingrese una contraseña"
                contra2.error = "Por favor, ingrese una contraseña"
            } else if (contraString != contra2String) {
                contra.error = "Las contraseñas no coinciden"
                contra2.error = "Las contraseñas no coinciden"
            } else {

                val nombreUsuario = nombreUsuarioEditText.text.toString()
                guardarDatosUsuario(nombreUsuario, contraString)

                Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, Top10Activity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    //  Funcion para manejar la logica de SharedPreferences yrecibe el nombre de usuario y la contraseña
    private fun guardarDatosUsuario(nombreUsuario: String?, contra: String?) {
        val editor = sharedPreferences.edit()
        editor.putBoolean("recordar_sesion", switchRecordarSesion.isChecked)

        if(switchRecordarSesion.isChecked) {
            // CAMBIO: Guardamos el nombre de usuario que se ingresó en el EditText
            editor.putString(getString(R.string.nombre), nombreUsuario)
            editor.putString(getString(R.string.password), contra) // ADVERTENCIA DE SEGURIDAD
        } else {
            editor.clear()
        }
        editor.apply()
    }

    //Función para volver a Registro al presionar el botón de retroceso
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}