package com.example.myapplication

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresPermission
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class InicioSesion_Activity : AppCompatActivity() {

    private lateinit var nombreUsuarioEditText: EditText
    private lateinit var contrasenaEditText: EditText
    private lateinit var verificacionCheckBox: CheckBox
    private lateinit var botonInicioSesion: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_inicio_sesion)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializar vistas
        nombreUsuarioEditText = findViewById(R.id.NombreUsuario)
        contrasenaEditText = findViewById(R.id.Contrasena)
        verificacionCheckBox = findViewById(R.id.Verificacion)
        botonInicioSesion = findViewById(R.id.BotonInicioSesion)

        // Recuperar preferencias
        val preferencias = getSharedPreferences(resources.getString(R.string.sp_credenciales), MODE_PRIVATE)
        val nombreGuardado = preferencias.getString(resources.getString(R.string.nombre), "")
        val passwordGuardada = preferencias.getString(resources.getString(R.string.password), "")

        if (!nombreGuardado.isNullOrEmpty() && !passwordGuardada.isNullOrEmpty()) {
            nombreUsuarioEditText.setText(nombreGuardado)
            contrasenaEditText.setText(passwordGuardada)
            verificacionCheckBox.isChecked = true
        }

        // Instancia de la base de datos
        val bd = AppDataBase.getDatabase(this)
        val usuarioDao = bd.usuarioDao()

        botonInicioSesion.setOnClickListener {
            val nombreUsuario = nombreUsuarioEditText.text.toString()
            val contrasena = contrasenaEditText.text.toString()
            val recordarUsuario = verificacionCheckBox.isChecked

            if (nombreUsuario.isEmpty()) {
                nombreUsuarioEditText.error = "El nombre de usuario no puede estar vacío"
                return@setOnClickListener
            }
            if (contrasena.isEmpty()) {
                contrasenaEditText.error = "La contraseña no puede estar vacía"
                return@setOnClickListener
            }

            // Llamada segura a la base de datos
            CoroutineScope(Dispatchers.IO).launch {
                val usuarioRegistrado = usuarioDao.getUsuarioPorNombre(nombreUsuario)
                val contrasenaRegistrada = usuarioDao.getContraseña(nombreUsuario)

                withContext(Dispatchers.Main) {
                    if (usuarioRegistrado == null) {
                        nombreUsuarioEditText.error = "El usuario no existe"
                        return@withContext
                    }

                    if (contrasenaRegistrada != contrasena) {
                        contrasenaEditText.error = "Contraseña incorrecta"
                        return@withContext
                    }

                    // Guardar preferencias si está marcado
                    if (recordarUsuario) login(nombreUsuario, contrasena)

                    Toast.makeText(this@InicioSesion_Activity, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
                    iniciarBienvenida(nombreUsuario)
                }
            }
        }

        // CheckBox para recordar usuario
        verificacionCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                pedirPermisos()
                crearCanalNotificaciones()
                mostrarNotificacion(nombreUsuarioEditText.text.toString())
                Toast.makeText(this, "Se recordará el usuario", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "No se recordará el usuario", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun iniciarBienvenida(usuario: String) {
        val intent = Intent(this, BienvenidaActivity::class.java)
        intent.putExtra(resources.getString(R.string.nombre), usuario)
        startActivity(intent)
        finish()
    }

    private fun login(usuario: String, password: String) {
        val preferencias = getSharedPreferences(resources.getString(R.string.sp_credenciales), MODE_PRIVATE)
        preferencias.edit().apply {
            putString(resources.getString(R.string.nombre), usuario)
            putString(resources.getString(R.string.password), password)
            apply()
        }
    }

    private fun pedirPermisos() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.POST_NOTIFICATIONS), 101)
            }
        }
    }

    private fun crearCanalNotificaciones() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("1", "Canal Recordatorio", NotificationManager.IMPORTANCE_DEFAULT)
            channel.description = "Canal para notificar recordatorios"
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    private fun mostrarNotificacion(usuario: String) {
        val pendingIntent = PendingIntent.getActivity(
            this, 0, Intent(this, BienvenidaActivity::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Crear el intent para cerrar la actividad
        val ignorePendingIntent = PendingIntent.getActivity(
            this, 0,
            Intent(this, BienvenidaActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                putExtra(resources.getString(R.string.nombre), usuario)
            },
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val largeIcon = BitmapFactory.decodeResource(resources, R.drawable.logo2)
        val builder = NotificationCompat.Builder(this, "1")
            .setSmallIcon(R.drawable.ic_notificacion)
            .setLargeIcon(largeIcon)
            .setContentTitle("Sesión recordada")
            .setContentText("Tu usuario ha sido recordado exitosamente.")
            .setStyle(NotificationCompat.BigTextStyle().bigText(
                "Tu usuario ha sido recordado exitosamente. Si quieres desactivar esta opción, vuelve a iniciar sesión."
            ))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setColor(ContextCompat.getColor(this, R.color.spotify_green))
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .addAction(
                R.drawable.ic_notificacion,
                "Ir a la app",
                ignorePendingIntent
            )

        NotificationManagerCompat.from(this).notify(0, builder.build())
    }
}

