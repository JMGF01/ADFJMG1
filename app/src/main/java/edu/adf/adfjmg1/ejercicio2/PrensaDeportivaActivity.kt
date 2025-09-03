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
        binding.diarioAS.settings.javaScriptEnabled = true
        binding.diarioAS.webViewClient = WebViewClient() // para evitar que se abra el navegador externo, y forzar a que se cargue en el webView
        binding.diarioAS.loadUrl(Constantes.URL_AS)

        binding.diarioMarca.settings.javaScriptEnabled = true
        binding.diarioMarca.webViewClient = WebViewClient()
        binding.diarioMarca.loadUrl(Constantes.URL_MARCA)

        binding.diarioMundoDeportivo.settings.javaScriptEnabled = true
        binding.diarioMundoDeportivo.webViewClient = WebViewClient()
        binding.diarioMundoDeportivo.loadUrl(Constantes.URL_MUNDODEPORTIVO)

        binding.diarioSport.settings.javaScriptEnabled = true
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

/**
 * OTRA APROXIMACIÓN DEL PROFESOR, MUY INTERESANTE PORQUE CREA EL PROCEDIMIENTO findViewsByType() PARA RECORRER RECURSIVAMENTE EL XML Y RECUPERAR
 * TODAS LAS VIEWS DE UN TIPO
 *
 * package edu.adf.profe
 *
 * import android.content.Intent
 * import android.content.Intent.ACTION_VIEW
 * import android.os.Bundle
 * import android.util.Log
 * import android.view.View
 * import android.view.ViewGroup
 * import android.widget.Button
 * import androidx.appcompat.app.AppCompatActivity
 * import androidx.core.net.toUri
 * import edu.adf.profe.databinding.ActivityPrensaBinding
 *
 *
 * class PrensaActivity : AppCompatActivity() {
 *
 *     val web1: String = "https://as.com/"
 *     val web2: String = "https://www.marca.com/"
 *     val web3: String = "https://www.mundodeportivo.com/"
 *     val web4: String = "https://www.sport.es/es/"
 *     val listaUrls = listOf(web1, web2, web3, web4)
 *
 *     lateinit var binding: ActivityPrensaBinding
 *
 *     override fun onCreate(savedInstanceState: Bundle?) {
 *         super.onCreate(savedInstanceState)
 *         binding = ActivityPrensaBinding.inflate(layoutInflater)
 *         setContentView(binding.root)
 *
 *         binding.webView1.settings.javaScriptEnabled = true
 *         binding.webView2.settings.javaScriptEnabled = true
 *         binding.webView3.settings.javaScriptEnabled = true
 *         binding.webView4.settings.javaScriptEnabled = true
 *
 *         binding.webView1.loadUrl(web1)
 *         binding.webView2.loadUrl(web2)
 *         binding.webView3.loadUrl(web3)
 *         binding.webView4.loadUrl(web4)
 *
 *         //programar los botones
 *         //1 obtengo los botones
 *         val lbotones = findViewsByType(binding.root, Button::class.java)
 *         Log.d(Constantes.ETIQUETA_LOG, "LA LISTA TIENE ${lbotones.size} BOTONES")
 *         lbotones.forEachIndexed { index, boton ->
 *             boton!!.tag = listaUrls[index]
 *         }
 *         lbotones.forEach{
 *             it!!.setOnClickListener {
 *                 val url = it.tag as String
 *                 val intentWeb = Intent(ACTION_VIEW, url.toUri())
 *                 startActivity(intentWeb)
 *             }
 *         }
 *
 *     }
 *
 *     private fun findViewsByType(
 *         vista_raiz: ViewGroup,
 *         tipo_buscado: Class<*>
 *     ): List<View?> {
 *         var lvistas: MutableList<View?>? = null
 *         var nhijos = 0
 *         var vactual: ViewGroup? = null
 *         var vistahija: View? = null
 *
 *         val lista_vistas: MutableList<ViewGroup> = ArrayList()
 *         lista_vistas.add(vista_raiz)
 *         lvistas = ArrayList()
 *
 *         var i = 0;
 *         do  {
 *             vactual = lista_vistas[i]
 *             Log.d("MIAPP", "Mostrando " + vactual.javaClass.canonicalName)
 *             nhijos = vactual.childCount
 *             for (j in 0..<nhijos) {
 *                 vistahija = vactual.getChildAt(j)
 *                 if (tipo_buscado.isAssignableFrom(vistahija.javaClass)) {
 *                     lvistas.add(vistahija)
 *                 }
 *                 if (vistahija is ViewGroup) {
 *                     lista_vistas.add(vistahija)
 *                 } else {
 *                     Log.d("MIAPP", "Mostrando " + vistahija.javaClass.canonicalName)
 *                 }
 *             }
 *             i++
 *         } while (i<lista_vistas.size)
 *         return lvistas
 *     }
 *
 * }
 *
 */