package com.example.myapplication

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class Registro : AppCompatActivity() {

    private lateinit var nombre: EditText
    private lateinit var apellido: EditText
    private lateinit var correo: EditText
    private lateinit var fecha: EditText
    private lateinit var continuar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Vinculación con los elementos del layout
        nombre = findViewById(R.id.idNombre)
        apellido = findViewById(R.id.idApellido)
        correo = findViewById(R.id.idEmail)
        fecha = findViewById(R.id.idFecha)
        continuar = findViewById(R.id.btnContinuar)

        // Instancia de la base de datos
        val bd = AppDataBase.getDatabase(this)
        val usuarioDao = bd.usuarioDao()

        // Mostrar calendario al tocar el EditText de fecha
        fecha.setOnClickListener { mostrarFecha() }

        continuar.setOnClickListener {
            val nombreString = nombre.text.toString()
            val apellidoString = apellido.text.toString()
            val correoString = correo.text.toString()
            val fechaString = fecha.text.toString()

            // Validación de campos vacíos
            if (nombreString.isEmpty() || apellidoString.isEmpty() || correoString.isEmpty() || fechaString.isEmpty()) {
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
                if (nombreString.isEmpty()) nombre.error = "Por favor, ingrese su nombre" else nombre.error = null
                if (apellidoString.isEmpty()) apellido.error = "Por favor, ingrese su apellido" else apellido.error = null
                if (correoString.isEmpty()) correo.error = "Por favor, ingrese su correo" else correo.error = null
                if (fechaString.isEmpty()) fecha.error = "Por favor, ingrese la fecha" else fecha.error = null
                return@setOnClickListener
            }

            // Validación básica de correo
            if (!correoString.contains("@")) {
                correo.error = "Por favor, ingresar un correo válido"
                Toast.makeText(this, "Debe ingresar un correo válido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else correo.error = null

            // Validación de edad
            val edad = calcularEdad(fechaString)
            if (edad < 13) {
                fecha.error = "Debes tener al menos 13 años"
                Toast.makeText(this, "Debes tener al menos 13 años para registrarte", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Comprobar si el correo ya está registrado usando coroutines
            CoroutineScope(Dispatchers.IO).launch {
                val usuarioExistente = usuarioDao.getUsuarioPorCorreo(correoString)
                withContext(Dispatchers.Main) {
                    if (usuarioExistente != null) {
                        correo.error = "El correo ya está registrado"
                        return@withContext
                    }

                    // Todo ok, navegar a Registro2
                    Toast.makeText(this@Registro, "Continuaremos con tu registro", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@Registro, Registro2::class.java).apply {
                        putExtra("nombre", nombreString)
                        putExtra("apellido", apellidoString)
                        putExtra("email", correoString)
                        putExtra("fecha", fechaString)
                    }
                    startActivity(intent)
                    finish()
                }
            }
        }
    }

    private fun mostrarFecha() {
        val calendario = Calendar.getInstance()
        val anioActual = calendario.get(Calendar.YEAR)
        val mesActual = calendario.get(Calendar.MONTH)
        val diaActual = calendario.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this, R.style.mostrarFecha,
            { _, anioSeleccionado, mesSeleccionado, diaSeleccionado ->
                val calendarioSeleccionado = Calendar.getInstance()
                calendarioSeleccionado.set(anioSeleccionado, mesSeleccionado, diaSeleccionado)
                val formatoFecha = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                fecha.setText(formatoFecha.format(calendarioSeleccionado.time))
                fecha.error = null
            }, anioActual, mesActual, diaActual
        )
        datePickerDialog.datePicker.maxDate = System.currentTimeMillis()
        datePickerDialog.show()
    }

    private fun calcularEdad(fechaNacimiento: String): Int {
        if (fechaNacimiento.isEmpty()) return 0
        val formatoFecha = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val fechaNacimientoDate = formatoFecha.parse(fechaNacimiento) ?: return 0

        val nacimientoCal = Calendar.getInstance()
        nacimientoCal.time = fechaNacimientoDate
        val hoy = Calendar.getInstance()
        var edad = hoy.get(Calendar.YEAR) - nacimientoCal.get(Calendar.YEAR)

        if (hoy.get(Calendar.MONTH) < nacimientoCal.get(Calendar.MONTH) ||
            (hoy.get(Calendar.MONTH) == nacimientoCal.get(Calendar.MONTH) &&
                    hoy.get(Calendar.DAY_OF_MONTH) < nacimientoCal.get(Calendar.DAY_OF_MONTH))) {
            edad--
        }
        return edad
    }
}
