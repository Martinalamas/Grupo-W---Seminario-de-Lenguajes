package com.example.myapplication

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UsuarioDao {

    @Query("SELECT * FROM usuarios WHERE usuario = :usuario AND contraseña = :contraseña")
    suspend fun getUsuario(usuario: String, contraseña: String): Usuario?

    @Query("SELECT * FROM usuarios WHERE usuario = :usuario")
    suspend fun getUsuarioPorNombre(usuario: String): Usuario?

    @Query("SELECT contraseña FROM usuarios WHERE usuario = :usuario")
    suspend fun getContraseña(usuario: String): String?

    @Query("SELECT * FROM usuarios WHERE email = :correo")
    suspend fun getUsuarioPorCorreo(correo: String): Usuario?

    @Insert
    suspend fun insertUsuario(usuario: Usuario)
}
