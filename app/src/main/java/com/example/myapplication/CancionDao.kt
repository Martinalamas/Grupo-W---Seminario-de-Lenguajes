package com.example.myapplication

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CancionDao {
    @Query("SELECT * FROM canciones")
    fun getAll(): List<Cancion>

    @Insert
    fun insertAll(canciones : Cancion)


}