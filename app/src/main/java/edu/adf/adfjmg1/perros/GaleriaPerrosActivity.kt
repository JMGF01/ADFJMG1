package edu.adf.adfjmg1.perros

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import edu.adf.adfjmg1.Constantes
import edu.adf.adfjmg1.databinding.ActivityGaleriaPerrosBinding
import edu.adf.adfjmg1.util.RedUtil
import kotlinx.coroutines.launch
import kotlin.math.abs

class GaleriaPerrosActivity : AppCompatActivity() {

    lateinit var binding: ActivityGaleriaPerrosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGaleriaPerrosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val raza_seleccionada = intent.getStringExtra("RAZA_ELEGIDA") ?: "hound"
        Log.d(Constantes.ETIQUETA_LOG, "GaleriaPerrosActivity: A buscar Fotos de  =  $raza_seleccionada")

        if (RedUtil.hayInternet(this)) {
            lifecycleScope.launch {
                try {
//                val fotosPerroService = FotosPerroRetrofitHelper.getFotosPerroServiceInstance(raza_seleccionada ?: "hound")
//                val listadoFotosPerro = fotosPerroService.obtenerFotosPerro()
                    val fotosPerroService = FotosPerroRetrofitHelper.getFotosPerroServiceInstance()
                    val listadoFotosPerro = fotosPerroService.obtenerFotosPerro(raza_seleccionada)
                    Log.d(
                        Constantes.ETIQUETA_LOG,
                        "Status de la petición: ${listadoFotosPerro.status}"
                    )
                    Log.d(
                        Constantes.ETIQUETA_LOG,
                        "Primera foto: ${listadoFotosPerro.message.get(0)}"
                    )

//                    for (i in listadoFotosPerro.message.indices) {
//                        Glide.with(this@GaleriaPerrosActivity)
//                            .load(listadoFotosPerro.message[i])
//                            .into(binding.ivPerro)
//                        delay(1500)
//                    }
                    mostrarFotosPerros(listadoFotosPerro, raza_seleccionada)
                } catch(ex: Exception) {
                    Log.e(Constantes.ETIQUETA_LOG, "ERROR AL CONECTARME AL API DE FOTOS DE PERROS", ex)
                    Toast.makeText(this@GaleriaPerrosActivity, "ERROR AL CONECTARME AL API", Toast.LENGTH_LONG)
                }
            }
        } else {
            val noti = Toast.makeText(this, "SIN CONEXIÓN A INTERNET", Toast.LENGTH_LONG)
            noti.show()
        }


    }

    fun mostrarFotosPerros(listaInternetFotosPerro: FotosPerro, raza: String): Unit {
        Log.d(Constantes.ETIQUETA_LOG, listaInternetFotosPerro.toString() )
        Log.d(Constantes.ETIQUETA_LOG, "HEMOS RX ${listaInternetFotosPerro.message.size} fotos" )

        var adapterPerrosFragment = AdapterPerrosFragment(this)
        adapterPerrosFragment.fotosRazaPerros = listaInternetFotosPerro
        binding.viewPager2.adapter = adapterPerrosFragment
        binding.razaPerro.text = raza


        binding.viewPager2.setPageTransformer { page, position ->
            when {
                position < -1 -> page.alpha = 0f
                position <= 0 -> {
                    page.alpha = 1f
                    page.translationX = 0f
                    page.scaleX = 1f
                    page.scaleY = 1f
                }
                position <= 1 -> {
                    page.alpha = 1 - position
                    page.translationX = page.width * -position
                    val scaleFactor = 0.75f + (1 - position) * 0.25f
                    page.scaleX = scaleFactor
                    page.scaleY = scaleFactor
                }
                else -> page.alpha = 0f
            }


            val zoomOutTransformer = ViewPager2.PageTransformer { page, position ->
                val scale = 1 - abs(position) * 0.2f
                page.scaleX = scale
                page.scaleY = scale
                page.alpha = 0.5f + (1 - abs(position)) * 0.5f
            }

        binding.viewPager2.setPageTransformer(zoomOutTransformer)

//        val fadeTransformer = ViewPager2.PageTransformer { page, position ->
//            page.alpha = 1 - abs(position)
        }
//
//        binding.viewPager2.setPageTransformer(fadeTransformer)



        //binding.viewPager2.setPageTransformer(depthTransformer)


    }
}