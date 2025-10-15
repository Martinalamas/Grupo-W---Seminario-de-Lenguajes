package com.example.myapplication

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "canciones")
data class Cancion(
    @ColumnInfo(name = "titulo") val titulo: String,
    @PrimaryKey(autoGenerate = true)
    var top: Int = 0,
    @ColumnInfo(name = "artista") var artista: String,
    @ColumnInfo(name = "duracion") var duracion: String,
)
