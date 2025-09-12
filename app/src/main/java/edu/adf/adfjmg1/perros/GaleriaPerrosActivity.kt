package edu.adf.adfjmg1.perros

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import edu.adf.adfjmg1.Constantes
import edu.adf.adfjmg1.R
import edu.adf.adfjmg1.databinding.ActivityGaleriaPerrosBinding
import edu.adf.adfjmg1.productos.ProductosRetrofitHelper
import edu.adf.adfjmg1.util.RedUtil
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GaleriaPerrosActivity : AppCompatActivity() {

    lateinit var binding: ActivityGaleriaPerrosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGaleriaPerrosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val raza_seleccionada = intent.getStringExtra("RAZA_ELEGIDA")
        Log.d(Constantes.ETIQUETA_LOG, "GaleriaPerrosActivity: A buscar Fotos de  =  $raza_seleccionada")

        if (RedUtil.hayInternet(this)) {
            lifecycleScope.launch {
                val fotosPerroService = FotosPerroRetrofitHelper.getFotosPerroServiceInstance(raza_seleccionada ?: "hound")
                val listadoFotosPerro = fotosPerroService.obtenerFotosPerro()
                Log.d(Constantes.ETIQUETA_LOG, "Status de la petición: ${listadoFotosPerro.status}")
                Log.d(Constantes.ETIQUETA_LOG, "Primera foto: ${listadoFotosPerro.message.get(0)}")

               for (i in listadoFotosPerro.message.indices)  {
                   Glide.with(this@GaleriaPerrosActivity)
                       .load(listadoFotosPerro.message[i])
                       .into(binding.ivPerro)
                   delay(1500)
               }
            }
        } else {
            val noti = Toast.makeText(this, "SIN CONEXIÓN A INTERNET", Toast.LENGTH_LONG)
            noti.show()
        }

    }
}