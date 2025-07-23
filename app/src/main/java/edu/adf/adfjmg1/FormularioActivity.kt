package edu.adf.adfjmg1

import android.app.Activity
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import edu.adf.adfjmg1.databinding.ActivityFormularioBinding
import java.util.zip.Inflater

class FormularioActivity : AppCompatActivity() {

    // para lanzar una subactividad (una actividad que me da un resultado)
    lateinit var lanzador: ActivityResultLauncher<Intent>
    lateinit var binding: ActivityFormularioBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFormularioBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // ocultada la barra (actionbar) desde el tema del Manifest
        // TODO ocultar el APPBar programáticamente

//        lanzador = registerForActivityResult(
//            ActivityResultContracts.StartActivityForResult(), {
//                it.
//            })

        lanzador = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult() //lo lque hay entre {} es el segundo parámetro de ActivityResultContracts.StartActivityForResult(), que lo sacamos fuera para que quede mas claro. Lo que lanzo es una actividad
        ){
            // la función que recibe el resultado
            result ->
                if (result.resultCode == Activity.RESULT_OK)
                {
                    Log.d("MIAPP_FORMULARIO","La subactividad ha FINALIZADO BIEN  ${result.resultCode}")
                    var intent_resultado = result.data
                    val color: Int = intent_resultado?.getIntExtra("COLOR_ELEGIDO", 0) ?: 0
                    binding.colorFavorito.setBackgroundColor(color)
                } else {
                    Log.d("MIAPP_FORMULARIO","La subactividad ha FINALIZADO BIEN  ${result.resultCode}")
                }

        }
    }

    fun seleccionarColorFavorito(view: View)
    {
        // DEBEMOS LANZAR LA OTRA ACTIVIDAD SUBCOLOR ACTIVITY, PERO
        val intent = Intent(this, SubColorActivity::class.java)
        //startActivity(intent)
        //startActivityForResult(intent, 99) // es como se hacía antiguamente, está deprecado, ya no se hace así
        lanzador.launch(intent) // aquí lanzo la subactividad
    }

    fun mostrarInfoFormulario(view: View) {
        //mostrar los datos del formulario
        Log.d("MIAPP_FORMULARIO", "NOMBRE = ${binding.editTextNombreFormulario.text.toString()} EDAD = ${binding.editTextEdadFormulario.text.toString()} HOMBRE = ${binding.radioButtonHombre.isChecked} MUJER = ${binding.radioButtonMujer.isChecked} MAYOR EDAD = ${binding.checkBox.isChecked}")
        // TODO crear una clase Usuario para albergar toda la información obtenida en el formulario.

    }


    /**
     *
    versión antigua
     */
    /*
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?,
        caller: ComponentCaller
    ) {
        super.onActivityResult(requestCode, resultCode, data, caller)
        //obtenía el resultado
    }

     */

}