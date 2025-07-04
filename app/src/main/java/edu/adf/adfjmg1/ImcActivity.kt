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

/**
 *
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
        enableEdgeToEdge()
        setContentView(R.layout.activity_imc)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun calcularImc(view: View) {
        // TODO hacer el cálculo del IMC
        // TODO MODIFICAR EL CÓDIGO PARA QUE AL TOAST LE ACOMPAÑE LA INFO DEL NÚMERO DE VECES QUE TOCA EL BOTÓN DE USUARIO.
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

    fun obtenerPeso(): Float
    {
        // obtiene el peso y lo devuelve
        // uso var cuando la variable declarada puede cambiar su valor después (mutable)
        var peso:Float = 0f //Inicializo la variable
        //obtengo el peso de la caja
        // cuando tenga una variable/dato que no cambia su valor, uso val
        val editTextPeso: EditText = findViewById<EditText>(R.id.editTextNumberDecimalPeso)
        peso = editTextPeso.text.toString().toFloat() //accedo al contenido y lo traduzco a número
        //tengo que devolver el peso
        return peso
    }

    fun obtenerAltura(): Float
    {
        // obtiene la altura y la devuelve
        var altura:Float = 0f
        val editTextAltura: EditText = findViewById<EditText>(R.id.editTextNumberDecimalAltura)
        altura = editTextAltura.text.toString().toFloat()
        return altura
    }

    /**
     * Delvuelve el cálculo de IMC usando la fórmula peso / altura * altura
     * @param peso: el peso
     * @param altura: la altura
     * @return el IMC calculado
     */
    fun obtenerImc(peso:Float, altura:Float): Float // PARÁMETROS FORMALES
    {
        var imc:Float = 0f
        // aplicamos la fórmula: IMC = peso / altura al cuadrado
        imc = peso / (altura * altura)
        return imc
    }

    fun mostrarResultado(imc:Float)
    {
        var resultadoImc = Toast.makeText(this, "Su IMC es $imc", Toast.LENGTH_LONG)
        resultadoImc.show()
    }

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