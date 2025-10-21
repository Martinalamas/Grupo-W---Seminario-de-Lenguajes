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
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresPermission
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.widget.SwitchCompat
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat

class Registro2 : AppCompatActivity() {

    // Declaración de variables
    lateinit var contra: EditText
    lateinit var contra2: EditText
    lateinit var btnRegistrar: Button
    lateinit var toolbar: Toolbar
    lateinit var titulo: TextView
    lateinit var switchRecordarSesion: SwitchCompat
    lateinit var nombreUsuarioEditText: EditText

    // SharedPreferences
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro2)

        // Vinculación de variables
        contra = findViewById(R.id.idContra)
        contra2 = findViewById(R.id.idContra2)
        btnRegistrar = findViewById(R.id.btnRegistrar)
        titulo = findViewById(R.id.idTitulo)
        toolbar = findViewById(R.id.toolbar)
        switchRecordarSesion = findViewById(R.id.idSwitch)
        nombreUsuarioEditText = findViewById(R.id.idNombreUsuario)

        sharedPreferences = getSharedPreferences(getString(R.string.sp_credenciales), MODE_PRIVATE)

        // Estado guardado
        val estadoGuardado = sharedPreferences.getBoolean("recordar_sesion", false)
        switchRecordarSesion.isChecked = estadoGuardado

        // Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Registro"
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.spotify_green))

        toolbar.setNavigationOnClickListener {
            val intent = Intent(this, Registro::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }

        // Recibir datos del registro anterior
        val nombre = intent.getStringExtra("nombre")
        val apellido = intent.getStringExtra("apellido")

        titulo.text = "¡Bienvenido $nombre $apellido!"

        // Validación y registro
        btnRegistrar.setOnClickListener {
            val nombreAnt = intent.getStringExtra("nombre")
            val correo = intent.getStringExtra("email")
            val fecha = intent.getStringExtra("fecha")
            val apellidoAnt = intent.getStringExtra("apellido")
            val contraString = contra.text.toString()
            val contra2String = contra2.text.toString()
            val nombreUsuario = nombreUsuarioEditText.text.toString()

            if (contraString.isEmpty() || contra2String.isEmpty()) {
                contra.error = "Por favor, ingrese una contraseña"
                contra2.error = "Por favor, ingrese una contraseña"
            } else if (contraString != contra2String) {
                contra.error = "Las contraseñas no coinciden"
                contra2.error = "Las contraseñas no coinciden"
            } else {
                val db = AppDataBase.getDatabase(this)
                val usuarioDao = db.usuarioDao()

                Thread {
                    try {
                        val existente = usuarioDao.getUsuarioPorNombre(nombreUsuario)
                        runOnUiThread {
                            if (existente != null) {
                                nombreUsuarioEditText.error = "El usuario ya existe"
                            } else {
                                val nuevoUsuario = Usuario(
                                    usuario = nombreUsuario,
                                    contraseña = contraString,
                                    nombre = nombreAnt.toString(),
                                    apellido = apellidoAnt.toString(),
                                    email = correo.toString(),
                                    fechaNacimiento = fecha.toString()
                                )
                                usuarioDao.insertUsuario(nuevoUsuario)
                                guardarDatosUsuario(nombreUsuario, contraString)
                                runOnUiThread {
                                    Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
                                    startActivity(Intent(this, Top10Activity::class.java))
                                    finish()
                                }
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }.start()
            }
        }
    }

    private fun guardarDatosUsuario(nombreUsuario: String?, contra: String?) {
        val editor = sharedPreferences.edit()
        editor.putBoolean("recordar_sesion", switchRecordarSesion.isChecked)

        if (switchRecordarSesion.isChecked) {
            editor.putString(getString(R.string.nombre), nombreUsuario)
            editor.putString(getString(R.string.password), contra)
            crearCanalNotificaciones()

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    mostrarNotificacion(nombreUsuarioEditText.text.toString())
                } else {
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                        101
                    )
                }
            } else {
                mostrarNotificacion(nombreUsuarioEditText.text.toString())
            }
        } else {
            editor.clear()
        }
        editor.apply()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
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
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    @RequiresPermission(value = "android.permission.POST_NOTIFICATIONS")
    private fun mostrarNotificacion(Usuario: String) {
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val ignorePendingIntent = PendingIntent.getActivity(
            this, 0,
            Intent(this, BienvenidaActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                putExtra(resources.getString(R.string.nombre), Usuario)
            },
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val largeIcon = BitmapFactory.decodeResource(resources, R.drawable.logo2)

        val builder = NotificationCompat.Builder(this, "1")
            .setSmallIcon(R.drawable.ic_notificacion)
            .setLargeIcon(largeIcon)
            .setContentTitle("Sesión recordada")
            .setContentText("Tu usuario ha sido recordado exitosamente.")
            .setStyle(
                NotificationCompat.BigTextStyle().bigText(
                    "Tu usuario ha sido recordado exitosamente. Si quieres desactivar esta opción, vuelve a iniciar sesión."
                )
            )
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

