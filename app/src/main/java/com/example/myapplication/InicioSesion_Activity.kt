package com.example.myapplication

import com.example.myapplication.BienvenidaActivity
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
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
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
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
        verificacionCheckBox.setOnCheckedChangeListener() { buttonView, isChecked ->
            if (isChecked) {
                pedirPermisos()
                crearCanalNotificaciones()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    if (ActivityCompat.checkSelfPermission(
                            this,
                            Manifest.permission.POST_NOTIFICATIONS
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        mostrarNotificacion(nombreUsuarioEditText.text.toString())
                    } else {
                        // Pedimos permiso
                        ActivityCompat.requestPermissions(
                            this,
                            arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                            101
                        )
                    }
                } else {
                    // Android < 13, no se necesita permiso
                    mostrarNotificacion(nombreUsuarioEditText.text.toString())
                }
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

    private fun pedirPermisos() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // Si no tiene permiso, lo solicita
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    101
                )
            }
        }
    }

    private fun crearCanalNotificaciones(){
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
    private fun mostrarNotificacion(Usuario : String) {


        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val ignorePendingIntent = PendingIntent.getActivity(
            this, 0,
            Intent(this, BienvenidaActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                putExtra(resources.getString(R.string.nombre), Usuario) // 👈 agregar esto
            },
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val builder = NotificationCompat.Builder(this, "1")
            .setSmallIcon(R.drawable.logo2)
            .setContentTitle("Sesión recordada")
            .setContentText("Tu usuario ha sido recordado exitosamente.")
            .setStyle(
                NotificationCompat.BigTextStyle().bigText(
                    "Tu usuario ha sido recordado exitosamente. Si quieres desactivar esta opción, vuelve a iniciar sesión."
                )
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setColor(ContextCompat.getColor(this, R.color.black))
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .addAction(
                R.drawable.logo2,
                "Ir a la app",
                ignorePendingIntent
            )

        NotificationManagerCompat.from(this).notify(0, builder.build())
    }






}
