package edu.adf.adfjmg1

data class Usuario(var nombre: String, var edad: Int, var sexo: Char, var esMayorEdad: Boolean, var colorFavorito: Int)
{
    // Si se quiere inicializar o meter lógica en el constructor
//    init {
//        require(edad >= 0) { "La edad no puede ser negativa" }
//    }
}