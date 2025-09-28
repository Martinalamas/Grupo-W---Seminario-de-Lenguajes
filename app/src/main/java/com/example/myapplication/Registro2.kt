package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.activity.enableEdgeToEdge
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class Registro2 : AppCompatActivity() {

    //Declaración de variables
    lateinit var contra : EditText
    lateinit var contra2: EditText
    lateinit var btnRegistrar : Button

    lateinit var toolbar: androidx.appcompat.widget.Toolbar

    lateinit var titulo : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro2)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }



        //Vinculacion de las varibles con su ID
        contra = findViewById(R.id.idContra)
        contra2 = findViewById(R.id.idContra2)
        btnRegistrar = findViewById(R.id.btnRegistrar)
        titulo= findViewById(R.id.idTitulo)
        toolbar = findViewById(R.id.toolbar)

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
        titulo.text= "¡Bienvenido $nombre $apellido!"

        //Al dar click, se validan los campos y si son validos, se muestra un mensaje de confirmacion
        btnRegistrar.setOnClickListener{
            val contraString = contra.text.toString()
            val contra2String = contra2.text.toString()

            //Verificacion de que los campos no esten vacios
            if (contraString.isEmpty() || contra2String.isEmpty()) {
                contra.error = "Por favor, ingrese una contraseña"
                contra2.error = "Por favor, ingrese una contraseña"
            }
            //Validacion de que las contraseñas sean iguales
            else if (contraString != contra2String) {
                contra.error = "Las contraseñas no coinciden"
                contra2.error = "Las contraseñas no coinciden"
            } else {
                //Si todo esta bien, se muestra un mensaje de confirmacion y se inicia la actividad Top10Activity
                Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, Top10Activity::class.java)
                startActivity(intent)
                finish()
            }
        }

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