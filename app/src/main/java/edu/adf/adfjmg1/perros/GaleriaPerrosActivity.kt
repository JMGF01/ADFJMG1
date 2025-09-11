package edu.adf.adfjmg1.perros

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import edu.adf.adfjmg1.Constantes
import edu.adf.adfjmg1.R
import edu.adf.adfjmg1.databinding.ActivityGaleriaPerrosBinding
import edu.adf.adfjmg1.productos.ProductosRetrofitHelper
import kotlinx.coroutines.launch

class GaleriaPerrosActivity : AppCompatActivity() {

    lateinit var binding: ActivityGaleriaPerrosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGaleriaPerrosBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_galeria_perros)

        val raza_seleccionada = intent.getStringExtra("RAZA_ELEGIDA")
        Log.d(Constantes.ETIQUETA_LOG, "GaleriaPerrosActivity: A buscar Fotos de  =  $raza_seleccionada")

        lifecycleScope.launch {
            val fotosPerroService = FotosPerroRetrofitHelper.getFotosPerroServiceInstance()
            val listadoFotosPerro = fotosPerroService.obtenerFotosPerro()
            Log.d(Constantes.ETIQUETA_LOG, "primer foto listado: ${listadoFotosPerro.status}")
        }

    }
}