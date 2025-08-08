package edu.adf.adfjmg1.ejercicio1

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import edu.adf.adfjmg1.Constantes
import edu.adf.adfjmg1.databinding.ActivityEjercicio1Binding
import androidx.core.graphics.toColorInt
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

lateinit var binding : ActivityEjercicio1Binding
lateinit var areaJuego: AreaTotalJuego

class Ejercicio1Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEjercicio1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // se comprueba si la pantalla se ha girado, si es así, se recuperan los colores almacenados, en caso contrario se inicializa con los colores por defecto.
        if (savedInstanceState != null) {
            areaJuego = savedInstanceState.getSerializable("areaJuego") as AreaTotalJuego
        } else {
            areaJuego = AreaTotalJuego()
        }

        pintaAreaJuego(areaJuego)
    }

    /**
     * colorea las 4 áreas rectangulares del juego.
     * @param areaJuego el objeto de juego que contiene las 4 áreas y sus colores
     */
    private fun pintaAreaJuego(areaJuego:AreaTotalJuego)
    {
        binding.area1.setBackgroundColor(areaJuego.area1.toColorInt())
        binding.area2.setBackgroundColor(areaJuego.area2.toColorInt())
        binding.area3.setBackgroundColor(areaJuego.area3.toColorInt())
        binding.area4.setBackgroundColor(areaJuego.area4.toColorInt())
        Log.d(Constantes.ETIQUETA_LOG_EJERCICIO_1,Constantes.AREA_JUEGO_PINTADA + areaJuego.coloresJuego[areaJuego.area1] + areaJuego.coloresJuego[areaJuego.area2] + areaJuego.coloresJuego[areaJuego.area3] + areaJuego.coloresJuego[areaJuego.area4])
    }

    /**
     * A medida que se pulsa en un área, se cambia su color a NEGRO.
     * Posteriormente se vuelve a pintar el área para actualizar su color,
     * y se comprueba si el juego debe finalizar (todas las áreas son negras)
     * @param view la vista que contiene las áreas del juego.
     */
    fun areaPulsada(view: View)
    {
        Log.d(Constantes.ETIQUETA_LOG_EJERCICIO_1, Constantes.AREA_JUEGO_PULSADA + view.resources.getResourceEntryName(view.id))

        when (view.id) {
            binding.area1.id -> areaJuego.area1 = Constantes.NEGRO
            binding.area2.id -> areaJuego.area2 = Constantes.NEGRO
            binding.area3.id -> areaJuego.area3 = Constantes.NEGRO
            binding.area4.id -> areaJuego.area4 = Constantes.NEGRO
        }

        pintaAreaJuego(areaJuego)
        compruebaFinJuego(areaJuego)
    }

    /**
     * Comprueba si todas las áreas del juego son negras.
     * Si es así, se finaliza la partida invocando a finJuego
     * @param areaJuego El área de juego a comprobar.
     */
    private fun compruebaFinJuego(areaJuego: AreaTotalJuego)
    {
        if (areaJuego.areaTotalJuegoEsNegra()) {
            finJuego()
        } else {
            Log.d(Constantes.ETIQUETA_LOG_EJERCICIO_1, Constantes.JUEGO_CONTINUA)
        }
    }

    /**
     * Se muestra un snackbar con el mensaje de fin de partida, y se finaliza el juego.
     */
    fun finJuego() {
        Log.d(Constantes.ETIQUETA_LOG_EJERCICIO_1, Constantes.FIN_JUEGO)
        val snackbar:Snackbar = Snackbar.make(binding.main, Constantes.FIN_JUEGO, BaseTransientBottomBar.LENGTH_LONG)

        //Se añade un listener al snackbar de modo que se sobreescriba la función onDimissed para que finalice la actividad cuando deje de mostrarse el mensaje y así de tiempo a leerlo.
        snackbar.addCallback(object : Snackbar.Callback() {
            override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                super.onDismissed(transientBottomBar, event)
                finish()
            }
        })
        snackbar.show()
    }

    /**
     * guarda el objeto areaJuego para recuperarlo posteriormente
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable("areaJuego", areaJuego)
    }
}