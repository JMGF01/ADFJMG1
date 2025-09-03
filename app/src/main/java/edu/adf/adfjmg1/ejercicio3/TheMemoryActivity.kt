package edu.adf.adfjmg1.ejercicio3

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import edu.adf.adfjmg1.Constantes
import edu.adf.adfjmg1.R
import edu.adf.adfjmg1.databinding.ActivityTheMemoryBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class TheMemoryActivity : AppCompatActivity() {

    lateinit var binding: ActivityTheMemoryBinding
    var tablero: Tablero = Tablero()
    lateinit var imageViewsCasillas: List<ImageView>
    var casillaPrevia: Casilla? = null
    var juegoActivo: Boolean = false
    var nombre: String = ""
    var puntuacion: Int = 0

    private var tiempoTranscurrido = 0
    private val tiempoLimite = 30 // segundos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTheMemoryBinding.inflate(layoutInflater)
        setContentView(binding.root)


        imageViewsCasillas = listOf(binding.casilla1, binding.casilla2, binding.casilla3, binding.casilla4, binding.casilla5, binding.casilla6, binding.casilla7, binding.casilla8,
                                                binding.casilla9, binding.casilla10, binding.casilla11, binding.casilla12, binding.casilla13, binding.casilla14, binding.casilla15, binding.casilla16)

        for (i in imageViewsCasillas.indices) {
            tablero.casillas[i].imageView = imageViewsCasillas[i]
        }

        pintaAreaJuego(tablero)

    }

    /**
     * Pinta el area de juego, dependiendo del valor visible de cada casilla del tablero
     * @param tablero el tablero a pintar
     */
    fun pintaAreaJuego(tablero: Tablero)
    {
        for (i in tablero.casillas.indices) {
            pintaCasilla(tablero.casillas[i])
        }
    }

    /**
     * pinta cada casilla dependiendo de su visibilidad:
     * si es visible -> la imagen
     * si es invisible -> su fondo
     * @param casilla La casilla a pintar
     */
    fun pintaCasilla(casilla: Casilla)
    {
        Log.d(Constantes.ETIQUETA_LOG_THE_MEMORY, "pintaCasilla: ${casilla.imageView?.id}")
        if (casilla.visible) {
            casilla.imageView?.setImageResource(casilla.imagen)
        }
        else
            casilla.imageView?.setImageResource(R.drawable.memory_fondo_bambu)
    }

    /**
     * ejecuta la comprobación de existencia de pareja al pulsar cada casilla
     * @param view la vista que contiene la casilla pulsada
     */
    fun casillaPulsada(view: View)
    {
        if (juegoActivo) {
            var casillaJugada = obtenerCasilla(view)

            if (!casillaJugada.visible) {
                casillaJugada.visible = true
                pintaCasilla(casillaJugada)

                casillaJugada.imageView?.post {  // El sigiuente código se ejecuta una vez que la imagen se haya pintado
                    Thread.sleep(500)
                    comprobarPareja(casillaJugada)
                }

//                if (comprobarFin(tablero)) {
//                    Toast.makeText(this, "FIN JUEGO \uD83C\uDF89", Toast.LENGTH_LONG).show()
//                    Thread.sleep(3500)
//                    guardarPuntuacion()
//                    mostrarClasificacion()
//                }
            }
        }
    }

    /**
     * dada una vista, obtiene la casilla asociada a la misma
     * @param view la vista sobre la que obtener la casilla
     * @return Casilla la casilla asociada a la vista
     */
    fun obtenerCasilla(view: View): Casilla
    {
        var nombreCasilla: String = view.resources.getResourceEntryName(view.id)
        Log.d(Constantes.ETIQUETA_LOG_THE_MEMORY, "La casilla pulsada es $nombreCasilla")

        val expReg = "\\d+".toRegex() //cogemos los dígitos numéricos del id del ImageView
        var indiceCasilla = expReg.find(nombreCasilla)?.value?.toInt() ?: 0
        Log.d(Constantes.ETIQUETA_LOG_THE_MEMORY, "Indice recuperado: $indiceCasilla")
        var casillaJugada = tablero.casillas[indiceCasilla-1]
        return casillaJugada
    }

    /**
     * comprueba si existe pareja
     * @param casillaJugada la casilla a comprobar si forma pareja
     */
    fun comprobarPareja(casillaJugada: Casilla)
    {
        if (casillaPrevia != null) {
            Log.d(Constantes.ETIQUETA_LOG_THE_MEMORY, "comprobarPareja -> Ya existe una casilla previa que se muestra")
            val casillaPrev: Casilla = casillaPrevia as Casilla // evitamos que se sigan realizando comprobaciones de null
            // como es la segunda pareja jugada, se comprueba si existe coincidencia
            if (casillaPrev.imagen != casillaJugada.imagen) {
                Log.d(
                    Constantes.ETIQUETA_LOG_THE_MEMORY,
                    "comprobarPareja -> La pareja NO COINCIDE"
                )
                ocultarCasillas(casillaPrev, casillaJugada)
            }
            else {
                puntuacion = puntuacion + 10000 // cada pareja suma 10000 puntos
            }
            casillaPrevia = null // Se vuelve a iniciar la jugada
        }
        else {
            Log.d(Constantes.ETIQUETA_LOG_THE_MEMORY, "comprobarPareja -> Primera casilla que se muestra")
            casillaPrevia = casillaJugada //es la primera casilla de la pareja jugada
        }
    }

    /**
     * oculta las casillas que forman una pareja que no coincide
     * @param casillaPrevia casilla que forma parte de la pareja a ocultar
     * @param casillaJugada casilla que forma parte de la pareja a ocultar
     */
    fun ocultarCasillas(casillaPrevia: Casilla, casillaJugada: Casilla)
    {
        Log.d(Constantes.ETIQUETA_LOG_THE_MEMORY, "ocultarCasillas -> Dando la vuelta a la pareja")
        casillaPrevia.visible = false
        pintaCasilla(casillaPrevia)
        casillaJugada.visible = false
        pintaCasilla(casillaJugada)
        Log.d(Constantes.ETIQUETA_LOG_THE_MEMORY, "ocultarCasillas -> Pareja ocultada")
    }

    /**
     * comprueba si ha finalizado la partida por haber encontrado todas las parejas
     * @return Boolean indicando si ha finalizado la partida
     */
    fun comprobarFin(tablero: Tablero): Boolean
    {
        return (tablero.casillas.all { it ->  it.visible == true})
    }

    /**
     * se ejecuta cuando se pulsa el botón de JUGAR, comprobando el nombre
     * e iniciando el cronómetro.
     * @param view la vista que contiene el botón.
     */
    fun jugarPulsado(view: View)
    {
        nombre = binding.editTextNombre.text.toString()

        if (esNombreValido(nombre)) {
            binding.editTextNombre.clearFocus() // quita el foco del campo de texto
            binding.editTextNombre.isEnabled = false

            // se oculta el teclado
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.editTextNombre.windowToken, 0)

            juegoActivo = true
            binding.botonJugar.isEnabled = false

            tablero.casillas.map {it -> it.imageView?.alpha = 1.0F }

            iniciarCronometro()
        }
    }

    /**
     * comprueba si el nombre es válido. Por ahora, se comprueba que se haya escrito algo.
     * @param nombre nombre a comprobar
     * @return Boolean el resultado de la comprobación.
     */
    fun esNombreValido (nombre:String): Boolean
    {
        return nombre.length > 1
    }

    /**
     *  inicia el cronómetro que limita el tiempo de juego
     */
    fun iniciarCronometro()
    {
        lifecycleScope.launch {
            while (tiempoTranscurrido < tiempoLimite) {
                delay(1000)
                tiempoTranscurrido++
                binding.textViewTiempo.text = "Tiempo: $tiempoTranscurrido s"

                if (tiempoTranscurrido >= tiempoLimite) {
                    finalizarJuego("¡TIEMPO AGOTADO! \uD83D\uDE13 ")
                    break
                } else if (comprobarFin(tablero)) {
                    finalizarJuego("FIN JUEGO \uD83C\uDF89")
                    break
                }
            }
        }
    }

    /**
     * finaliza el juego al llegar al límite de tiempo
     * y muestra la clasificación
     */
    fun finalizarJuego(mensajeFin: String) {
        juegoActivo = false
//        Toast.makeText(this, "¡TIEMPO AGOTADO! \uD83D\uDE13 ", Toast.LENGTH_LONG).show()
        Toast.makeText(this, mensajeFin, Toast.LENGTH_LONG).show()
        Thread.sleep(3500)

        guardarPuntuacion()
        mostrarClasificacion()
    }

    /**
     * guarda la puntuación en un fichero SharedPreferences
     */
    fun guardarPuntuacion()
    {
        val ficheroPuntuaciones = getSharedPreferences("TheMemoryPuntuaciones", MODE_PRIVATE)
        val editor = ficheroPuntuaciones.edit()
        puntuacion = puntuacion - tiempoTranscurrido * 500

        editor.putInt("nombre_$nombre", puntuacion)
        editor.apply()
        editor.commit()
    }

    /**
     * muestra la clasificación
     */
    fun mostrarClasificacion()
    {
        val intent = Intent(this, ClasificacionActivity::class.java)
        startActivity(intent)
    }

}