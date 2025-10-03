package edu.adf.adfjmg1.fechayhora

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.DialogFragment
import edu.adf.adfjmg1.Constantes
import edu.adf.adfjmg1.R
import edu.adf.adfjmg1.databinding.ActivitySeleccionFechaYhoraBinding

class SeleccionFechaYHoraActivity : AppCompatActivity(), View.OnFocusChangeListener {

    lateinit var binding: ActivitySeleccionFechaYhoraBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySeleccionFechaYhoraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cajaFecha.onFocusChangeListener = this //con esto, digo que cuando la caja tome el foco
        binding.cajaHora.onFocusChangeListener = this //el cursor esté ahí, se llama esa función
    }

    override fun onFocusChange(caja: View?, tieneFoco: Boolean) {
        //TODO("Not yet implemented")
        if (tieneFoco)
        {
            Log.d(Constantes.ETIQUETA_LOG, "FOCO GANADO")
            when (caja?.id)
            {
                R.id.cajaHora ->
                {
                    Log.d(Constantes.ETIQUETA_LOG, "La caja hora ha ganado el foco")
                    //creamos el fragmet
                    val dialogoFragmentReloj: DialogFragment = SeleccionHora()
                    //lo visualizamos
                    dialogoFragmentReloj.show(supportFragmentManager, "RELOJ")

                }
                R.id.cajaFecha ->
                {
                    Log.d(Constantes.ETIQUETA_LOG, "La caja fecha ha ganado el foco")
                    //creamos el fragmet
                    val dialogoFragmentCalendario: DialogFragment = SeleccionFecha()
                    //lo visualizamos
                    dialogoFragmentCalendario.show(supportFragmentManager, "CALENDARIO")
                }

            }
        }
    }


    fun actualizarHoraSeleccionada (hora:String)
    {
        this.binding.cajaHora.setText(hora)

    }

    fun actualizarFechaSeleccionada (fecha:String)
    {
        this.binding.cajaFecha.setText(fecha)

    }
}