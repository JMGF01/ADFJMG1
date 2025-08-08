package edu.adf.adfjmg1.ejercicio1

import edu.adf.adfjmg1.Constantes
import java.io.Serializable

data class AreaTotalJuego(

    var area1:String = Constantes.ROJO,
    var area2:String = Constantes.AZUL,
    var area3:String = Constantes.VERDE,
    var area4:String = Constantes.AMARILLO

): Serializable
{
    // mapa para obtener el nombre del color a partir de su valor
    val coloresJuego: Map<String, String> = mapOf(
        Constantes.ROJO to Constantes.NOMBRE_ROJO,
        Constantes.AZUL to Constantes.NOMBRE_AZUL,
        Constantes.VERDE to Constantes.NOMBRE_VERDE,
        Constantes.AMARILLO to Constantes.NOMBRE_AMARILLO,
        Constantes.NEGRO to Constantes.NOMBRE_NEGRO
    )

    /**
     * Comprueba si las 4 áreas de juego son negras
     */
    fun areaTotalJuegoEsNegra(): Boolean
    {
        val listaAreasJuego:List<String> = listOf(area1, area2, area3, area4)
        return listaAreasJuego.all { it == Constantes.NEGRO }
    }

}


