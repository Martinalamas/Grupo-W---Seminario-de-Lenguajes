package com.example.myapplication

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios")
data class Usuario(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo (name = "usuario") var usuario: String,
    @ColumnInfo (name = "contraseña") var contraseña: String,
    @ColumnInfo (name = "nombre") var nombre: String,
    @ColumnInfo (name = "apellido") var apellido: String,
    @ColumnInfo (name = "email") var email: String,
    @ColumnInfo (name = "fechaNacimiento") var fechaNacimiento: String

)