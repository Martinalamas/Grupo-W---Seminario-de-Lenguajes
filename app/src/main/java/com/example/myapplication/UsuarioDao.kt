package com.example.myapplication

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UsuarioDao {
    @Query("SELECT * FROM usuarios WHERE usuario = :usuario AND contraseña = :contraseña")
    fun getUsuario(usuario: String, contraseña: String): Usuario?

    @Query("SELECT * FROM usuarios WHERE usuario = :usuario")
    fun getUsuarioPorNombre(usuario: String): Usuario?

    @Query("SELECT contraseña FROM usuarios WHERE usuario = :usuario")
    fun getContraseña(usuario: String): String?

    @Query("SELECT * FROM usuarios WHERE email = :correo")
    fun getUsuarioPorCorreo(correo: String): Usuario?


    @Insert
    fun insertUsuario(usuario: Usuario)

    companion object


}