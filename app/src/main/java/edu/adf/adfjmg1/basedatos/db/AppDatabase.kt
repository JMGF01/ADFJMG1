package edu.adf.profe.basedatos.db

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import edu.adf.adfjmg1.Constantes
import edu.adf.adfjmg1.basedatos.converter.Conversor
import edu.adf.adfjmg1.basedatos.dao.CocheDao
import edu.adf.adfjmg1.basedatos.dao.EmpleoDao
import edu.adf.adfjmg1.basedatos.dao.PersonaDao
import edu.adf.adfjmg1.basedatos.entity.Coche
import edu.adf.adfjmg1.basedatos.entity.Empleo
import edu.adf.adfjmg1.basedatos.entity.Persona
import java.util.concurrent.Executors

@Database(entities = [Persona::class, Empleo::class, Coche::class], version = 1)
@TypeConverters(Conversor::class) // Para que guarde las fechas como timestamp y los enumerados
abstract class AppDatabase : RoomDatabase() {
    abstract fun personaDao(): PersonaDao
    abstract fun empleoDao(): EmpleoDao
    abstract fun cocheDao(): CocheDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "personas_db"
                ).setQueryCallback({ sqlQuery, bindArgs ->
                    Log.d(Constantes.ETIQUETA_LOG, "Consulta SQL: $sqlQuery, Parámetros: $bindArgs")
                }, Executors.newSingleThreadExecutor())
                    .build().also {
                    INSTANCE = it
                }
            }
        }
    }
}