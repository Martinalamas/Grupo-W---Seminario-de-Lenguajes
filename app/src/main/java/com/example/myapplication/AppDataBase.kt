package com.example.myapplication

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Cancion::class, Usuario::class], version = 2)



abstract class AppDataBase : RoomDatabase() {
    abstract fun cancionDao(): CancionDao
    abstract fun usuarioDao(): UsuarioDao

    companion object {
        private var INSTANCIA: AppDataBase? = null
            fun getDatabase(context: Context): AppDataBase {
                if(INSTANCIA == null){
                    synchronized(this){
                        INSTANCIA = Room.databaseBuilder(
                            context,
                            AppDataBase::class.java, "canciones_database")
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build()
                    }
                }
                return INSTANCIA!!
            }
        }

}
