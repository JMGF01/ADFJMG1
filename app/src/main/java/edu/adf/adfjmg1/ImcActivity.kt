package edu.adf.adfjmg1

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

/**
 *  SI TU IMC ESTÁ POR DEBAJO DE 16, TU IMC ES DESNUTRIDO
 *  SI TU IMC ES MAYOR O IGUAL A 16 Y MENOR QUE 18 --> DELGADO
 *  SI TU IMC ES MAYOR O IGUAL A 18 Y MENOR QUE 25 --> IDEAL
 *  SI TU IMC ES MAYOR O IGUAL A 25 Y MENOR QUE 31 --> SOBREPESO
 *  SI TU IMC ES MAYOR O IGUAL QUE 31 --> OBESO
 */

class ImcActivity : AppCompatActivity() {

    var numeroVecesBoton:Int = 0 // Para llevar la cuenta de veces que el usuario toca un botón.
    var resultadoNombre:String = "" //variable de ámbito global/de clase

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("MIAPP_IMC", "en OnCreate")
        super.onCreate(savedInstanceState)
        Locale.setDefault(Locale.US)
        enableEdgeToEdge()
        setContentView(R.layout.activity_imc)
        // si bundle está a null no hago nada, si es distinto de null, pillo el resultadoNombre y actualizo el valor de la caja
        if ( savedInstanceState != null)
        {
            Log.d("MiImcActivity","El saco tiene cosas. La actividad viene de recrearse")
            resultadoNombre = savedInstanceState.getString("resultadoNombre","")
            val cajaResultado = findViewById<TextView>(R.id.imcResultado)
            cajaResultado.text = resultadoNombre
            cajaResultado.visibility = View.VISIBLE
        }
        else {
            Log.d("MIAPP_IMC","La actividad se ha creado por primera vez")
        }

        // usando características "avanzadas" de kotlin
        resultadoNombre = savedInstanceState?.getString("resultadoNombre") ?: "" //con el interrogante sólo se va a invocar si no es null
        // ?: se llama operador Elvis (Elvis operator)

        /*
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        */
    }

    // PARA DIBUJAR UN MENÚ EN LA PARTE SUPERIOR DEBO DEFINIR EL
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_imc, menu)
        return super.onCreateOptionsMenu(menu)
    }

    // Este método es invocado cuando el usuario toca una opción del menú
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId)
        {
            R.id.opcionSalir -> {
                Log.d("MiImcActivity", "El usuario quiere salir")
                //finish()

                // Si nos confirma --> haremos finish
                // si no quiere --> borrar el cuadro de dialogo
                // TODO: 1) Hacerlo i18N con inglés o el que queráis
                // TODO: 2) añadir el botón de neutro y usar cancel para el neutro, usando el cancel del diálogo
                var dialogo = AlertDialog.Builder(this)
                    .setTitle(resources.getString(R.string.imc_tituloAviso))
                    .setMessage(resources.getString(R.string.imc_mensajeAviso))
                    .setIcon(R.drawable.imagen_derrota)
//                    .setPositiveButton("SÍ", fun(dialogo: DialogInterface, opcion: Int){
//                        this.finish()
//                    }) /* requiere un listener, es decir, poner una función que se ejecuta cuando el usuario dice sí */
//                    .setNegativeButton("NO", fun (dialogo: DialogInterface, opcion: Int){
//                        dialogo.dismiss()
//                    })
                    .setPositiveButton(R.string.imc_avisoSI){ dialogo, opcion ->
                        Log.d("MIAPP_IMC", "Opción positiva salir = $opcion")
                        this.finish()
                    } // es el segundo parámetro, pero está definido fuera
                    .setNegativeButton(R.string.imc_avisoNO){ dialogo: DialogInterface, opcion: Int ->
                        Log.d("MIAPP_IMC", "Opción positiva salir = $opcion")
                        dialogo.dismiss()
                    }
                    .setNeutralButton(R.string.imc_avisoCANCEL){dialogo, opcion ->
                        Log.d("MIAPP_IMC", "Opción neutral salir = $opcion")
                        dialogo.cancel()
                    }

                dialogo.setOnCancelListener{
                    Log.d("MIAPP_IMC", "HA CANCELADO EL DIÁLOGO")
                }

//                dialogo.setOnCancelListener(fun (dialogo: DialogInterface){
//                    Log.d("MiImcActivity", "HA CANCELADO EL DIÁLOGO")
//                })

                dialogo.show() //lo muestro

            }
            R.id.opcionLimpiar -> {
                Log.d("MIAPP_IMC", "El usuario quiere salir")
                // cojo las cajas de texto y las pongo a vacío
                findViewById<EditText>(R.id.editTextNumberDecimalPeso).setText("")
                findViewById<EditText>(R.id.editTextNumberDecimalAltura).setText("")
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onStart() {
        super.onStart()
        Log.d("MIAPP_IMC", "en onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("MIAPP_IMC", "en onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("MIAPP_IMC", "en onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("MIAPP_IMC", "en onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MIAPP_IMC", "en onDestroy")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d("MIAPP_IMC", "en OnSaveInstanceState")
        //1 obtengo el nombre del resultado de la caja
        //2 lo guardo
        // otra opción es declarar la variable de ámbito global
        //tengo que guardar resultadoNombre
        outState.putString("resultadoNombre", resultadoNombre) // guardo resultadoNombre en el saco de Bundle
    }

    fun calcularImc(view: View)
    {
        Log.d("MIAPP_IMC","El usuario ha tocado el botón de calcular IMC")
        // creamos la notificación/mensaje
        //informarNumVecesBoton()
        //IMC = peso / altura al cuadrado
        // 1 obtener el peso introducido
        var peso:Float = obtenerPeso()
        Log.i("MIAPP_IMC","Peso introducido: $peso" )
        // 2 obtener la altura introducida
        val altura:Float = obtenerAltura()
        Log.i("MIAPP_IMC","Altura introducida: $altura" )
        // 3 hacer la fórmula del IMC - PARÁMETROS ACTUALES
        val imc:Float = obtenerImc(peso, altura)
        Log.i("MIAPP_IMC","Imc calculado: $peso" )
        // 4 mostrar el resultado
        //mostrarResultado(imc)

        resultadoNombre = traducirResultadoImcVersionIF(imc)
        val tvresultado = findViewById<TextView>(R.id.imcResultado)
        tvresultado.text = resultadoNombre
        tvresultado.visibility = View.VISIBLE

        mostrarResultadoCaja(imc,evaluarResultado(imc))
        //TODO transitar a una ventana nueva ImagenResultadoActivity
        val intent:Intent = Intent(this, ImagenResultadoActivity::class.java) // declaración del intent para ir a la ventana nueva (parámetros: context, clase a la que ir)
        intent.putExtra("resultado", evaluarResultado(imc))  //Aquí estoy guardando del saco
        startActivity(intent)

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
     * Evalua el IMC dado:
     * - SI TU IMC ESTÁ POR DEBAJO DE 16 - TU IMC ES DESNUTRIDO
     * - SI TU IMC ES MAYOR O IGUAL A 16 Y MENOR QUE 18 --> DELGADO
     * - SI TU IMC ES MAYOR O IGUAL A 18 Y MENOR QUE 25 --> IDEAL
     * - SI TU IMC ES MAYOR O IGUAL A 25 Y MENOR QUE 31 --> SOBREPESO
     * - SI TU IMC ES MAYOR O IGUAL QUE 31 --> OBESO
     * @param imc el IMC a evaluar:
     *
     * @return la evaluación del IMC
     */
    fun evaluarResultado(imc:Float):String
    {
        var evaluacionIMC:String = ""
        // formateo del IMC a dos decimales
        var imcFormateado:String = String.format("%.2f", imc)

 /*       when (imcFormateado.toFloat()) {
            in 0f..15.99f -> evaluacionIMC = "¡ESTÁS DESNUTRIDO!"
            in 16.0f..17.99f -> evaluacionIMC= "¡ESTÁS DELGADO!"
            in 18.0f..24.99f -> evaluacionIMC = "¡ESTÁS IDEAL!"
            in 25.0f..30.99f -> evaluacionIMC = "¡TIENES SOBREPESO!"
            in 31.0f..Float.MAX_VALUE -> evaluacionIMC = "¡ESTÁS OBESO!"
            else -> evaluacionIMC = "¡ESTÁS FUERA DE RANGO!"
        }
*/
        when (imcFormateado.toFloat()) {
            in 0f..15.99f -> evaluacionIMC = "DESNUTRIDO"
            in 16.0f..17.99f -> evaluacionIMC= "DELGADO"
            in 18.0f..24.99f -> evaluacionIMC = "IDEAL"
            in 25.0f..30.99f -> evaluacionIMC = "SOBREPESO"
            in 31.0f..Float.MAX_VALUE -> evaluacionIMC = "OBESO"
            else -> evaluacionIMC = "FUERA DE RANGO"
        }

        return evaluacionIMC
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
            in 0f..15.99f -> evaluacionIMC = resources.getString(R.string.imc_desnutrido)
            in 16.0f..17.99f -> evaluacionIMC= resources.getString(R.string.imc_delgado)
            in 18.0f..24.99f -> evaluacionIMC = resources.getString(R.string.imc_ideal)
            in 25.0f..30.99f -> evaluacionIMC = resources.getString(R.string.imc_sobrepeso)
            in 31.0f..Float.MAX_VALUE -> evaluacionIMC = resources.getString(R.string.imc_obeso)
            else -> evaluacionIMC = resources.getString(R.string.imc_fueraRango)
        }

        var resultadoImc = Toast.makeText(this, "Su IMC es $imcFormateado \n $evaluacionIMC", Toast.LENGTH_LONG)
        resultadoImc.show()
    }

    /**
     * Muestra un texto en una caja previamente invisible,
     * indicando el IMC obtenido y su evaluación
     * @param imc el IMC calculado
     * @param evaluacionIMC la evaluación del IMC
     */
    fun mostrarResultadoCaja(imc:Float ,evaluacionIMC:String)
    {
        // formateo del IMC a dos decimales
        var imcFormateado:String = String.format("%.2f", imc)

        val tvresultado = findViewById<TextView>(R.id.imcResultado)
        //tvresultado.text = "Su IMC es $imcFormateado \n $evaluacionIMC"
        tvresultado.text = resources.getString(R.string.imc_mensajeResultado, imcFormateado, evaluacionIMC)
        tvresultado.visibility = View.VISIBLE
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