package edu.adf.adfjmg1

import android.os.Bundle
import android.text.BoringLayout
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import edu.adf.adfjmg1.databinding.ActivitySpinnerBinding
import java.util.zip.Inflater

class SpinnerActivity : AppCompatActivity() {

    lateinit var binding: ActivitySpinnerBinding
    val arrayVisibilidad = arrayOf("VISIBLE", "INVISIBLE", "GONE")
    var primeraVez: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySpinnerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // INICIAMOS EL SPINNER - EL SPINNER TIENE UN ADAPTER (proveedor)
        val spinnerAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayVisibilidad) // (this, android.R.layout.simple_spinner_item) esa línea es para darle un estilo cuando está plegada, sacada de la clase R de Android (no de mi clase R)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) // cuando la lista se despliegue, hay que indicarle tambien un estilo (un estilo para la clase desplegada).
        binding.spinner.adapter = spinnerAdapter

        // CASO ESPECIAL PORQUE AdapterView.OnItemSelectedListener TIENE 2 MÉTODOS ABSTRACTOS
        // SI SÓLO TUVIERA UNO (ONCLICK) PODRÍAMOS HABERLO IMPLEMENTADO CON UNA FUNCIÓN LAMBDA/ANÓNIMA
        binding.spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener { // como requiere dos funciones como parámetros, lo podemos hacer definiendo de manera explícita dichas funciones de esta forma, creando un objeto.
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (primeraVez)
                {
                    Log.d("MIAPP_SPINNER", "ES LA PRIMERA VEZ QUE SE CARGA EL SPINNER (el usuario en realidad no ha tocado el spinner)")
                    primeraVez = false
                }
                else {
                    Log.d("MIAPP_SPINNER", "OPCIÓN $position SELECCIONADA")
                    // Si toca el cero --> visible - la imagen la dejo visible
                    // Si toca el uno --> invisible - la imagen la dejo en invisible
                    // Si toca el dos --> gone - la imagen la dejo en gone
                    when (position) {
                        0 -> binding.imagenMuestra.visibility = View.VISIBLE
                        1 -> binding.imagenMuestra.visibility = View.INVISIBLE
                        2 -> binding.imagenMuestra.visibility = View.GONE
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Log.d("MIAPP_SPINNER", "OPCIÓN ELIMINADA DEL SPINNER") // ESTO NUNCA PASARÁ
            }
        }
    }
}