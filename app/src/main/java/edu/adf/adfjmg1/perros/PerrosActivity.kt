package edu.adf.adfjmg1.perros

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import edu.adf.adfjmg1.Constantes
import edu.adf.adfjmg1.R
import edu.adf.adfjmg1.databinding.ActivityPerrosBinding

class PerrosActivity : AppCompatActivity() {

    lateinit var binding: ActivityPerrosBinding
    var primeraVez = true
    var razaSeleccionada = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPerrosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initActivity()
    }

    private fun initActivity() {
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.razas,
            android.R.layout.simple_spinner_item
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) // cuando la lista se despliegue, hay que indicarle tambien un estilo (un estilo para la clase desplegada).
        binding.spinnerRazas.adapter = adapter

        binding.spinnerRazas.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener { // como requiere dos funciones como parámetros, lo podemos hacer definiendo de manera explícita dichas funciones de esta forma, creando un objeto.
            override fun onItemSelected(
                parent: AdapterView<*>?,
                opcionTocada: View?,
                position: Int,
                id: Long
            ) {
                razaSeleccionada = (opcionTocada as TextView).text.toString()
                Log.d(Constantes.ETIQUETA_LOG,"Raza seleccionada: $razaSeleccionada")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Log.d(Constantes.ETIQUETA_LOG, "OPCIÓN ELIMINADA DEL SPINNER") // ESTO NUNCA PASARÁ
            }
        }
    }

    fun buscarFotos(view: View)
    {
        Log.d(Constantes.ETIQUETA_LOG, "A buscar Fotos de  =  $razaSeleccionada")

        //TODO
        /**
         * 1 TRANSITO A GALERIA PERROS ACTIVITY PASANDO EN EL INTENT LA RAZA
         * 2 OBTENGO EL LISTADO DE FOTOS DE ESA RAZA LLAMANDO A https://dog.ceo/api/breed/{RAZA}/images
         * 3
         */

        val intent = Intent(this, GaleriaPerrosActivity::class.java)
        intent.putExtra ("RAZA_ELEGIDA", razaSeleccionada)
        startActivity(intent)
    }

}