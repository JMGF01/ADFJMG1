package edu.adf.adfjmg1.basedatos.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "personas")
data class Persona(
    @PrimaryKey(autoGenerate = true) val id:Int = 0, val nombre:String, val edad:Int)
