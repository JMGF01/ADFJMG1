package edu.adf.adfjmg1

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Locale

/**
 *  SI TU IMC ESTÁ POR DEBAJO DE 16, TU IMC ES DESNUTRIDO
 *  SI TU IMC ES MAYOR O IGUAL A 16 Y MENOR QUE 18 --> DELGADO
 *  SI TU IMC ES MAYOR O IGUAL A 18 Y MENOR QUE 25 --> IDEAL
 *  SI TU IMC ES MAYOR O IGUAL A 25 Y MENOR QUE 31 --> SOBREPESO
 *  SI TU IMC ES MAYOR O IGUAL QUE 31 --> OBESO
 */
// TODO: Pasar del valor número al valor nominal --> ESTÁS IDEAL

class ImcActivity : AppCompatActivity() {

    var numeroVecesBoton:Int = 0 // Para llevar la cuenta de veces que el usuario toca un botón.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Locale.setDefault(Locale.US)
        enableEdgeToEdge()
        setContentView(R.layout.activity_imc)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun calcularImc(view: View)
    {
        Log.d("MiImcActivity","El usuario ha tocado el botón de calcular IMC")
        // creamos la notificación/mensaje
        //informarNumVecesBoton()
        //IMC = peso / altura al cuadrado
        // 1 obtener el peso introducido
        var peso:Float = obtenerPeso()
        Log.i("MiImcActivity","Peso introducido: $peso" )
        // 2 obtener la altura introducida
        val altura:Float = obtenerAltura()
        Log.i("MiImcActivity","Altura introducida: $altura" )
        // 3 hacer la fórmula del IMC - PARÁMETROS ACTUALES
        val imc:Float = obtenerImc(peso, altura)
        Log.i("MiImcActivity","Imc calculado: $peso" )
        // 4 mostrar el resultado
        mostrarResultado(imc)
        // cerrar la activity (si hubiera una ventana/activity apilada -detrás-, se mostraría, si no, se sale de la app.
        //finish()
        // salir de la app / cerrar todas las ventanas.
        //finishAffinity()
    }

    /**
     * Obtiene el peso y lo devuelve.
     * @return peso: El peso obtenido.
     */
    fun obtenerPeso(): Float
    {
        // uso var cuando la variable declarada puede cambiar su valor después (mutable)
        var peso:Float = 0f //Inicializo la variable
        //obtengo el peso de la caja
        // cuando tenga una variable/dato que no cambia su valor, uso val
        val editTextPeso: EditText = findViewById<EditText>(R.id.editTextNumberDecimalPeso)
        peso = editTextPeso.text.toString().toFloat() //accedo al contenido y lo traduzco a número
        //tengo que devolver el peso
        return peso
    }

    /**
     * Obtiene la altura y la devuelve.
     * @return altura: la altura obtenida.
     */
    fun obtenerAltura(): Float
    {
        var altura:Float = 0f
        val editTextAltura: EditText = findViewById<EditText>(R.id.editTextNumberDecimalAltura)
        altura = editTextAltura.text.toString().toFloat()
        return altura
    }

    /**
     * Delvuelve el cálculo de IMC usando la fórmula: peso / altura * altura
     * @param peso: el peso.
     * @param altura: la altura.
     * @return imc: el IMC calculado.
     */
    fun obtenerImc(peso:Float, altura:Float): Float // PARÁMETROS FORMALES
    {
        var imc:Float = 0f
        // aplicamos la fórmula: IMC = peso / altura al cuadrado
        imc = peso / (altura * altura)
        return imc
    }

    /**
     * Muestra y evalúa el IMC formateado a 2 decimales a través de un Toast
     * @param imc: el IMC previamente calculado
     */
    fun mostrarResultado(imc:Float)
    {
        /**
         *  SI TU IMC ESTÁ POR DEBAJO DE 16, TU IMC ES DESNUTRIDO
         *  SI TU IMC ES MAYOR O IGUAL A 16 Y MENOR QUE 18 --> DELGADO
         *  SI TU IMC ES MAYOR O IGUAL A 18 Y MENOR QUE 25 --> IDEAL
         *  SI TU IMC ES MAYOR O IGUAL A 25 Y MENOR QUE 31 --> SOBREPESO
         *  SI TU IMC ES MAYOR O IGUAL QUE 31 --> OBESO
         */
        var evaluacionIMC:String = ""
        // formateo del IMC a dos decimales
        var imcFormateado:String = String.format("%.2f", imc)

        when (imcFormateado.toFloat()) {
            in 0f..15.99f -> evaluacionIMC = "¡ESTÁS DESNUTRIDO!"
            in 16.0f..17.99f -> evaluacionIMC= "¡ESTÁS DELGADO!"
            in 18.0f..24.99f -> evaluacionIMC = "¡ESTÁS IDEAL!"
            in 25.0f..30.99f -> evaluacionIMC = "¡TIENES SOBREPESO!"
            in 31.0f..Float.MAX_VALUE -> evaluacionIMC = "¡ESTÁS OBESO!"
            else -> evaluacionIMC = "¡ESTÁS FUERA DE RANGO!"
        }

        var resultadoImc = Toast.makeText(this, "Su IMC es $imcFormateado \n $evaluacionIMC", Toast.LENGTH_LONG)
        resultadoImc.show()
    }

    /**
     * Muestra y evalúa el IMC formateado a 2 decimales a través de un Toast,
     * utilizando estructuras if
     * @param resultado: el IMC calculado previamente.
     * @return la evaluación del IMC
     */
    fun traducirResultadoImcVersionIF(resultado:Float):String
    {
        var imcResultado:String = ""

        //TODO completar el cuerpo de la función o con IF o con WHEN
        if (resultado<16) {
            imcResultado = "DESNUTRIDO"
        } else if (resultado>=16 && resultado<18)
        {
            imcResultado = "DELGADO"
        } else if (resultado>=18 && resultado <25)
        {
            imcResultado = "IDEAL"
        } else if (resultado>=25 && resultado<31)
        {
            imcResultado = "SOBREPESO"
        } else if (resultado>=31)
        {
            imcResultado = "OBESO"
        }

        return imcResultado
    }

    /**
     * Muestra con un Toast el número de veces que el usuario pulsa el botón de calcular.
     */
    fun informarNumVecesBoton()
    {
        numeroVecesBoton = ++numeroVecesBoton
        //val miToast:Toast = Toast.makeText(this, "El usuario ha tocado el botón de calcular IMC: " + numeroVecesBoton + " veces", Toast.LENGTH_LONG)
        // con operador plantilla $, no hace falta concatenar con +
        val miToast:Toast = Toast.makeText(this, "El usuario ha tocado el botón de calcular IMC: $numeroVecesBoton veces", Toast.LENGTH_LONG)
        // mostrar la notificación
        miToast.show()
    }

}