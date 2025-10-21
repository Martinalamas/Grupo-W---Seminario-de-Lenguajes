package com.example.myapplication

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresPermission
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Registro2 : AppCompatActivity() {

    private lateinit var contra: EditText
    private lateinit var contra2: EditText
    private lateinit var btnRegistrar: Button
    private lateinit var toolbar: Toolbar
    private lateinit var titulo: TextView
    private lateinit var switchRecordarSesion: SwitchCompat
    private lateinit var nombreUsuarioEditText: EditText
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro2)

        // Vinculación
        contra = findViewById(R.id.idContra)
        contra2 = findViewById(R.id.idContra2)
        btnRegistrar = findViewById(R.id.btnRegistrar)
        titulo = findViewById(R.id.idTitulo)
        toolbar = findViewById(R.id.toolbar)
        switchRecordarSesion = findViewById(R.id.idSwitch)
        nombreUsuarioEditText = findViewById(R.id.idNombreUsuario)

        // SharedPreferences
        sharedPreferences = getSharedPreferences(getString(R.string.sp_credenciales), MODE_PRIVATE)
        switchRecordarSesion.isChecked = sharedPreferences.getBoolean("recordar_sesion", false)

        // Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Registro"
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.spotify_green))
        toolbar.setNavigationOnClickListener {
            finish() // vuelve a Registro
        }

        // Recibir datos del paso anterior
        val nombre = intent.getStringExtra("nombre") ?: ""
        val apellido = intent.getStringExtra("apellido") ?: ""
        val correo = intent.getStringExtra("email") ?: ""
        val fecha = intent.getStringExtra("fecha") ?: ""

        titulo.text = "¡Bienvenido $nombre $apellido!"

        // Botón registrar
        btnRegistrar.setOnClickListener {
            val contraStr = contra.text.toString()
            val contra2Str = contra2.text.toString()
            val nombreUsuario = nombreUsuarioEditText.text.toString()

            // Validaciones
            if (contraStr.isEmpty() || contra2Str.isEmpty()) {
                contra.error = "Por favor, ingrese una contraseña"
                contra2.error = "Por favor, ingrese una contraseña"
                return@setOnClickListener
            }

            if (contraStr != contra2Str) {
                contra.error = "Las contraseñas no coinciden"
                contra2.error = "Las contraseñas no coinciden"
                return@setOnClickListener
            }

            if (nombreUsuario.isEmpty()) {
                nombreUsuarioEditText.error = "Ingrese un nombre de usuario"
                return@setOnClickListener
            }

            val db = AppDataBase.getDatabase(this)
            val usuarioDao = db.usuarioDao()

            // Coroutine para Room
            CoroutineScope(Dispatchers.IO).launch {
                val existente = usuarioDao.getUsuarioPorNombre(nombreUsuario)
                withContext(Dispatchers.Main) {
                    if (existente != null) {
                        nombreUsuarioEditText.error = "El usuario ya existe"
                        return@withContext
                    }

                    // Insertar nuevo usuario
                    val nuevoUsuario = Usuario(
                        usuario = nombreUsuario,
                        contraseña = contraStr,
                        nombre = nombre,
                        apellido = apellido,
                        email = correo,
                        fechaNacimiento = fecha
                    )

                    CoroutineScope(Dispatchers.IO).launch {
                        usuarioDao.insertUsuario(nuevoUsuario)
                    }

                    // Guardar SharedPreferences y notificación
                    guardarDatosUsuario(nombreUsuario, contraStr)

                    // Mensaje y navegación
                    Toast.makeText(this@Registro2, "Registro exitoso", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@Registro2, BienvenidaActivity::class.java))
                    finish()
                }
            }
        }
    }

    private fun guardarDatosUsuario(nombreUsuario: String, contra: String) {
        val editor = sharedPreferences.edit()
        editor.putBoolean("recordar_sesion", switchRecordarSesion.isChecked)
        if (switchRecordarSesion.isChecked) {
            editor.putString(getString(R.string.nombre), nombreUsuario)
            editor.putString(getString(R.string.password), contra)
            crearCanalNotificaciones()
            pedirPermisoYMostrarNotificacion(nombreUsuario)
        } else {
            editor.clear()
        }
        editor.apply()
    }

    private fun pedirPermisoYMostrarNotificacion(usuario: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(
                    this, Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                mostrarNotificacion(usuario)
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    101
                )
            }
        } else {
            mostrarNotificacion(usuario)
        }
    }

    private fun crearCanalNotificaciones() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "1",
                "Canal Recordatorio",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = "Canal para notificar recordatorios"
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    private fun mostrarNotificacion(usuario: String) {
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val ignorePendingIntent = PendingIntent.getActivity(
            this, 0,
            Intent(this, BienvenidaActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                putExtra(getString(R.string.nombre), usuario)
            },
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val builder = NotificationCompat.Builder(this, "1")
            .setSmallIcon(R.drawable.ic_notificacion)
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.logo2))
            .setContentTitle("Sesión recordada")
            .setContentText("Tu usuario ha sido recordado exitosamente.")
            .setStyle(NotificationCompat.BigTextStyle().bigText(
                "Tu usuario ha sido recordado exitosamente. Si quieres desactivar esta opción, vuelve a iniciar sesión."
            ))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setColor(ContextCompat.getColor(this, R.color.spotify_green))
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .addAction(R.drawable.ic_notificacion, "Ir a la app", ignorePendingIntent)

        NotificationManagerCompat.from(this).notify(0, builder.build())
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}

