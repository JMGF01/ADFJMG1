package edu.adf.adfjmg1.ejercicio3

import edu.adf.adfjmg1.R

class Tablero {

    var casillas: List<Casilla>

    init {
        val imagenes: List<Int> = listOf(
            R.drawable.memory_gato,
            R.drawable.memory_loro,
            R.drawable.memory_perro,
            R.drawable.memory_tigre,
            R.drawable.memory_tucan,
            R.drawable.memory_ardilla,
            R.drawable.memory_camaleon,
            R.drawable.memory_conejo
        )

        val parejas: List<Int> = (imagenes + imagenes).shuffled() // se duplican y se mezclan las imágenes

        casillas = parejas.map { it -> Casilla(imagen = it) }

    }
}

