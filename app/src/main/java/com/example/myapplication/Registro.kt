package com.example.myapplication

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import java.util.Locale
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.SimpleDateFormat
import java.util.Calendar

class Registro : AppCompatActivity() {

    //Declaración de variables
    lateinit var nombre : EditText
    lateinit var apellido : EditText
    lateinit var correo : EditText
    lateinit var fecha : EditText
    lateinit var continuar : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Vinculación
        nombre = findViewById(R.id.idNombre)
        apellido= findViewById(R.id.idApellido)
        correo = findViewById(R.id.idEmail)
        fecha = findViewById(R.id.idFecha)
        continuar = findViewById(R.id.btnContinuar)

        //Vinculación base de datos
        val bd = AppDataBase.getDatabase(this)
        val usuarioDao = bd.usuarioDao()

        //Al dar click, aparece el calendario para seleccionar la fecha de nacimiento
        fecha.setOnClickListener{
            mostrarFecha()
        }

        //Validaciones y navegación a Registro2
        continuar.setOnClickListener{
            val nombreString = nombre.text.toString()
            val apellidoString = apellido.text.toString()
            val correoString = correo.text.toString()
            val fechaString = fecha.text.toString()

            //Validación de campos
            if (nombreString.isEmpty() || apellidoString.isEmpty() || correoString.isEmpty() || fechaString.isEmpty()) {
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
                if (nombreString.isEmpty()) nombre.error = "Por favor, ingrese su nombre" else nombre.error = null
                if (apellidoString.isEmpty()) apellido.error = "Por favor, ingrese su apellido" else apellido.error = null
                if (correoString.isEmpty()) correo.error = "Por favor, ingrese su correo" else correo.error = null
                if (fechaString.isEmpty()) fecha.error = "Por favor, ingrese la fecha" else fecha.error = null
                return@setOnClickListener
            }

            //Validación de correo
            if (!correoString.contains("@")) {
                Toast.makeText(this, "Debe ingresar un correo valido", Toast.LENGTH_SHORT).show()
                correo.error = "Por favor, ingresar un correo valido"
                return@setOnClickListener
            } else {
                correo.error = null
            }

            //Validacion correo registrado
            val usuarioRegistrado = usuarioDao.getUsuarioPorCorreo(correoString)
            if (usuarioRegistrado != null) {
                correo.error = "El correo ya está registrado"
                return@setOnClickListener
            }

            //Validación de edad
            val edad = calcularEdad(fechaString)
            if (fechaString.isNotEmpty() && edad < 13) {
                fecha.error = "Debes tener al menos 13 años"
                Toast.makeText(this, "Debes tener al menos 13 años para registrarte", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            //Intent a Registro2
            Toast.makeText(this, "Continuaremos con tu registro", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, Registro2::class.java)
            intent.putExtra("nombre", nombreString)
            intent.putExtra("apellido", apellidoString)
            intent.putExtra("email", correoString)
            intent.putExtra("fecha", fechaString)
            startActivity(intent)
            finish()
        }
    }

    //Función para mostrar el calendario
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
                val fechaFormateada = formatoFecha.format(calendarioSeleccionado.time)
                fecha.setText(fechaFormateada)
                fecha.error = null
            },
            anioActual,
            mesActual,
            diaActual
        )

        datePickerDialog.datePicker.maxDate = System.currentTimeMillis()
        datePickerDialog.show()
    }

    //Función para calcular la edad
    private fun calcularEdad(fechaNacimiento: String): Int {
        if (fechaNacimiento.isEmpty()) return 0
        val formatoFecha = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val fechaNacimientoDate = formatoFecha.parse(fechaNacimiento) ?: return 0

        val fechaNacimientoCalendar = Calendar.getInstance()
        fechaNacimientoCalendar.time = fechaNacimientoDate

        val fechaActualCalendar = Calendar.getInstance()
        var edad = fechaActualCalendar.get(Calendar.YEAR) - fechaNacimientoCalendar.get(Calendar.YEAR)

        if (fechaActualCalendar.get(Calendar.MONTH) < fechaNacimientoCalendar.get(Calendar.MONTH) ||
            (fechaActualCalendar.get(Calendar.MONTH) == fechaNacimientoCalendar.get(Calendar.MONTH) &&
                    fechaActualCalendar.get(Calendar.DAY_OF_MONTH) < fechaNacimientoCalendar.get(Calendar.DAY_OF_MONTH))) {
            edad--
        }

        return edad
    }
}


