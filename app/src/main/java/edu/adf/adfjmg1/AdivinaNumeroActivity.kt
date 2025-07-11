package edu.adf.adfjmg1

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlin.random.Random

/**
 * Esta actividad muestra un juego para que el usuario adivine un número
 * Al inicio, el programa piensa un número entre 1 y 100, que será secreto
 * y es el que debe adivinar el usuario.
 *
 * El usuario dispone de 5 intentos para adivinar el número secreto.
 * Por cada intento, si falla, el programa le debe informar de si el número buscado
 * es menor o mayor que el número introducido.
 *
 * Se van restando las vidas, y si se consumen los 5 intentos sin acertar, le mostramos
 * al usuario un mensaje de derrota y el número buscado.
 *
 * Si el usuario acierta, le mostramos un mensaje de ENHORABUENA
 */
class AdivinaNumeroActivity : AppCompatActivity() {

    var numeroSecreto:Int = 0
    var numeroVidas:Int = 5
    lateinit var etTextoFinal:TextView

    // REGIÓN DE TEXTOS
    val textoFinalEnhorabuena:String = "¡ENHORABUENA, LO HAS ADIVINADO!"
    val textoFinalFallo:String = "GAME OVER, EL NÚMERO ERA "
    val numeroMenor:String = "El número secreto es MENOR"
    val numeroMayor:String = "El número secreto es MAYOR"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_adivina_numero)

        etTextoFinal = findViewById<TextView>(R.id.textoFinal)
        this.numeroSecreto = savedInstanceState?.getInt("numSecreto") ?: generarNumeroSecreto()
        this.numeroVidas = savedInstanceState?.getInt("numVidas") ?: 5
        findViewById<TextView>(R.id.numVidas).text = numeroVidas.toString() + " VIDAS"
        var finalText = savedInstanceState?.getString("textoFinal") ?: ""
        when {
            finalText.equals(textoFinalEnhorabuena) -> muestraTextoFinal(textoFinalEnhorabuena)
            finalText.equals(textoFinalFallo) -> muestraTextoFinal(textoFinalFallo + numeroSecreto)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d("MIAPPADIVINA","onSaveInstanceState")
        outState.putInt("numVidas", this.numeroVidas)
        outState.putInt("numSecreto", this.numeroSecreto)

        if (etTextoFinal.visibility == View.VISIBLE ) {
            when {
                numeroVidas == 0 -> outState.putString("textoFinal", this.textoFinalFallo)
                numeroVidas > 0 -> outState.putString("textoFinal", this.textoFinalEnhorabuena)
            }
        }
    }

    fun intentoAdivina(view: View) {
        Log.d("MIAPPADIVINA","El usuario ha dado a probar")

        when {
            this.numeroVidas > 1 -> comprobarIntento() // Se comprueba si el usuario ha acertado
            this.numeroVidas == 1 -> {   // El usuario ha gastado su último intento y ha fallado
                actualizarNumeroVidas()
                muestraTextoFinal(this.textoFinalFallo + numeroSecreto)
            }
            else -> {
                Log.d("MIAPPADIVINA","El juego ha finalizado pero el usuario sigue jugando, finalizando la ejecución")
                finishAffinity()
            }
        }
    }

    /**
     * Se comprueba si el usuario ha acertado el número:
     * - si acierta, se muestra un mensaje de felicitación
     * - si falla, se muestra una pista
     */
    fun comprobarIntento()
    {
        var numeroUsuario = obtenerNumeroUsuario()

        if (numeroUsuario == numeroSecreto) {
            muestraTextoFinal(this.textoFinalEnhorabuena)
            //this.numeroVidas = 0  // Se actualiza numeroVidas a 0 para evitar que se pueda seguir jugando
            Log.d("MIAPPADIVINA","comprobarIntento -> El usuario ha acertado")
        } else {
            actualizarNumeroVidas()
            mostrarPista(numeroUsuario)
            Log.d("MIAPPADIVINA","comprobarIntento -> El usuario ha fallado")
        }
    }

    /**
     * Dada una imagen por su id de la clase R, la muestra en el ImageView imagenAdivina
     * @param imagen el id de la imagen a mostrar
     */
    fun actualizarImagen (imagen:Int) {
        //findViewById<ImageView>(R.id.imagenAdivina).setImageResource(imagen)
        Glide.with(this)
            .asGif()
            .load(imagen)
            .into(findViewById<ImageView>(R.id.imagenAdivina))
    }

    /**
     * Muestra un mensaje a modo de pista:
     * - si el número del usuario es menor, la pista le indica que el númro secreto es mayor.
     * - si el número del usuario es mayor, la pista le indica que el númro secreto es menor.
     */
    fun mostrarPista(numUsuario:Int)
    {
        if (numUsuario < numeroSecreto) {
            mostrarToast(this.numeroMayor)
        } else {
            mostrarToast(this.numeroMenor)
        }
    }

    /**
     * Muestra mediante un Toast el mensaje proporcionado
     * @param texto el mensaje a mostrar
     */
    fun mostrarToast(texto:String)
    {
        Toast.makeText(this, texto, Toast.LENGTH_LONG).show()
        Log.d("MIAPPADIVINA","Pista mostrada: $texto")
    }

    /**
     * Muestra el texto proporcionado en el textView textoFinal
     * @param texto el texto a mostrar
     */
    fun muestraTextoFinal(texto:String)
    {
//        var etTextoFinal:TextView = findViewById<TextView>(R.id.textoFinal)

        etTextoFinal.text = texto
        etTextoFinal.visibility = View.VISIBLE
        Log.d("MIAPPADIVINA","Texto final mostrado: $texto")
        findViewById<Button>(R.id.botonJugar).isEnabled = false // Deshabilita el botón
        when {
            texto.equals(textoFinalEnhorabuena) -> actualizarImagen(R.drawable.imagen_victoria)
            else -> actualizarImagen(R.drawable.imagen_derrota)
        }
    }

    /**
     *  Actualiza el textView numVidas con el número de vidas actual.
     */
    fun actualizarNumeroVidas()
    {
        this.numeroVidas = --this.numeroVidas
        var tvNumeroVidas:TextView = findViewById<TextView>(R.id.numVidas)

        tvNumeroVidas.text = this.numeroVidas.toString() + " VIDAS"
        Log.d("MIAPPADIVINA","Número de vídas actualizado: $this.numeroVidas")
    }

    /**
     * Genera un número secreto aleatorio
     * @return el número aleatorio generado
     */
    fun generarNumeroSecreto():Int
    {
        var numeroSecretoLocal:Int = 0

        numeroSecretoLocal = Random.nextInt(1,100)
        Log.d("MIAPPADIVINA","El número secreto generado es: $numeroSecretoLocal")

        return numeroSecretoLocal
    }

    /**
     * Obtiene el número introducido por el usuario.
     * @return el número jugado.
     */
    fun obtenerNumeroUsuario():Int
    {
        var numUsuario:Int = 0

        var etNumeroUsuario:EditText = findViewById<EditText>(R.id.numeroUsuario)
        numUsuario = etNumeroUsuario.text.toString().toInt()
        Log.d("MIAPPADIVINA", "El número introducido por el usuario es: $numUsuario")

        return numUsuario
    }
}