package edu.adf.adfjmg1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import edu.adf.adfjmg1.databinding.ActivityWebViewBinding

class WebViewActivity : AppCompatActivity() {

    val webAdf:String = "https://adf-formacion.es/"
    val rutaLocalWebAdf: String = "file:///android_asset/adf.html"
    lateinit var binding: ActivityWebViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //activo Javascript
        binding.webview.settings.javaScriptEnabled = true
        //si cargo una página sin el permiso de internet en la app, fallará. Hay que añadirlo en el Manifest.
//        binding.webview.loadUrl(webAdf) // con esto cargamos la página web en nuestra webview
        binding.webview.loadUrl(rutaLocalWebAdf)
    }
}