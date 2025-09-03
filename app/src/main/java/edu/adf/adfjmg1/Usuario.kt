package edu.adf.adfjmg1

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class Usuario(var nombre: String, var edad: Int, var sexo: Char, var esMayorEdad: Boolean, var colorFavorito: Int):Parcelable
{
    // Si se quiere inicializar o meter lógica en el constructor
//    init {
//        require(edad >= 0) { "La edad no puede ser negativa" }
//    }
}