package edu.adf.adfjmg1.ejercicio2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import edu.adf.adfjmg1.Constantes
import edu.adf.adfjmg1.R
import edu.adf.adfjmg1.databinding.ActivityPrensaDeportivaBinding

class PrensaDeportivaActivity : AppCompatActivity() {

    lateinit var binding: ActivityPrensaDeportivaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrensaDeportivaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cargaInicialWebs()
    }

    /**
     * Inicializa los webView con sus webs correspondientes.
     */
    private fun cargaInicialWebs() {
        binding.diarioAS.webViewClient = WebViewClient() // para evitar que se abra el navegador externo, y forzar a que se cargue en el webView
        binding.diarioAS.loadUrl(Constantes.URL_AS)

        binding.diarioMarca.webViewClient = WebViewClient()
        binding.diarioMarca.loadUrl(Constantes.URL_MARCA)

        binding.diarioMundoDeportivo.webViewClient = WebViewClient()
        binding.diarioMundoDeportivo.loadUrl(Constantes.URL_MUNDODEPORTIVO)

        binding.diarioSport.webViewClient = WebViewClient()
        binding.diarioSport.loadUrl(Constantes.URL_SPORT)

        Log.d(Constantes.ETIQUETA_LOG_PRENSA_DEPORTIVA, Constantes.WEBS_INICIALIZADAS)
    }

    /**
     * Se invoca al pulsar el botón de VisitarWeb.
     * Abre un navegador con la url de la web seleccionada.
     */
    fun visitarWeb(view: View)
    {
        var url:String = ""

        when (view.id) {
            R.id.botonAS -> url = Constantes.URL_AS
            R.id.botonMarca -> url = Constantes.URL_MARCA
            R.id.botonMundoDeportivo -> url = Constantes.URL_MUNDODEPORTIVO
            R.id.botonDiarioSport -> url = Constantes.URL_SPORT
        }

        Log.d(Constantes.ETIQUETA_LOG_PRENSA_DEPORTIVA, Constantes.VISITAR_WEB_PULSADO + url)
        Toast.makeText(this, Constantes.ABRIENDO_NAVEGADOR, Toast.LENGTH_SHORT).show()

        val intentWeb = Intent(Intent.ACTION_VIEW, url.toUri())
        startActivity(intentWeb)

        // finish() //se comenta para permitir que al retroceder desde el navegador se vuelva a ver la selección de prensa deportiva.
    }
}