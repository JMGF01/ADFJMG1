package edu.adf.adfjmg1.ejercicio3

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import edu.adf.adfjmg1.Constantes
import edu.adf.adfjmg1.databinding.ActivityClasificacionBinding

class ClasificacionActivity : AppCompatActivity() {

    lateinit var binding: ActivityClasificacionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityClasificacionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val listaClasificaciones: MutableList<Puntuacion> = recuperaClasificacionOrdenada()
        mostrarClasifiacion(formateaClasificacion(listaClasificaciones))
    }
       
    /**
     * recupera todas las clasificaciones guardadas en el fichero "TheMemoryPuntuaciones" de tipo SharedPreferences,
     * las ordena de forma descendiente y las devuelve en una MutableList
     * @return la Mutablelist de clasificaciones ordenadas de forma descendiente. 
     */
    fun recuperaClasificacionOrdenada(): MutableList<Puntuacion> {
        val prefs = getSharedPreferences("TheMemoryPuntuaciones", MODE_PRIVATE)
        val clasificaciones = prefs.all
        val listaPuntuaciones = mutableListOf<Puntuacion>()

        for ((clave, valor) in clasificaciones) {
            if (clave.startsWith("nombre_")) {
                var puntuacion: Puntuacion = Puntuacion(clave.removePrefix("nombre_"), valor.toString().toInt())
                listaPuntuaciones.add(puntuacion)
            }
        }

        listaPuntuaciones.sortByDescending { it -> it.puntos }
        
        return listaPuntuaciones
    }

    fun formateaClasificacion(listaClasificaciones: MutableList<Puntuacion>): List<Puntuacion>{
        listaClasificaciones.mapIndexed { index, puntuacion ->
            when (index) {
                0 -> puntuacion.nombre = "🥇" + puntuacion.nombre
                1 -> puntuacion.nombre = "🥈" + puntuacion.nombre
                2 -> puntuacion.nombre = "🥉" + puntuacion.nombre
//                else -> puntuacion.nombre = "⭐" + puntuacion.nombre
            }
        }
        return listaClasificaciones
    }
    
    /**
     * Muestra la clasifiación en el TextView
     * @param listaClasificaciones la lista con las clasifiaciones a mostrar
     */
    private fun mostrarClasifiacion(listaClasificaciones: List<Puntuacion>)
    {
        binding.textViewRanking.text = listaClasificaciones.joinToString("\n") {
            "${it.nombre + Constantes.SEPARADOR + it.puntos}"
        }
    }

    /**
     * Termina la actividad al pulsar el botón Salir
     * @param view: el view correspondiente al botón
     */
    fun salir(view: View) {
        finishAffinity()
    }

//    val textoRanking = rankingOrdenado.mapIndexed { index, it ->
//        val medalla = when (index) {
//            0 -> "🥇"
//            1 -> "🥈"
//            2 -> "🥉"
//            else -> "⭐"
//        }
//        "$medalla ${it.nombre}: ${it.puntos} ms"
//    }.joinToString("\n")
}