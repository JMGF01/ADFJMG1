package edu.adf.adfjmg1

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import edu.adf.adfjmg1.databinding.ActivityBusquedaBinding
import androidx.core.net.toUri

class BusquedaActivity : AppCompatActivity() {

    /**
     * VER
     * +
     * https://www.google.com/search?q=real+madrid
     *
     * -->
     *
     *  Me abre el navegador web de mi dispositivo
     */

    lateinit var binding: ActivityBusquedaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBusquedaBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun botonBuscar(view: View) {
        // obtener el término de búsqueda introducido por el usuario
        val busqueda:String = binding.textoBusqueda.text.toString()
        // lanzar un intent para buscar en Google
        // https://www.google.com/search?q=real+madrid
        Log.d("MIAPP_BUSQUEDA", "El usuario quiere buscar $busqueda")
        val url:String = "https://google.com/search?q=$busqueda"
        val web:Uri = url.toUri() // para eliminar espacios, tildes, la url la formamos bien con este método
        val intentBusqueda = Intent(Intent.ACTION_VIEW, web) // INTENT IMPLÍCITO (en el propio INTENT estoy diciendo lo que quiero hacer)
        try {
            startActivity(Intent.createChooser(intentBusqueda, "ELIGE NAVEGADOR"))
//            startActivity(intentBusqueda)
//            if (intentBusqueda.resolveActivity(packageManager)!= null) {
//                Log.d("MIAPP_BUSQUEDA", "El dispositivo puede navegar por Internet")
////            startActivity(intentBusqueda)
//                startActivity(Intent.createChooser(intentBusqueda, "ELIGE NAVEGADOR"))
//            } else {
//                Toast.makeText(this, "No se ha detectado un navegador", Toast.LENGTH_LONG).show()
//                val intentTienda = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.android.chrome"))
//                Log.d("MIAPP_BUSQUEDA", "Le invitamos a que instale Google Chrome de la tienda")
//                if (intentTienda.resolveActivity(packageManager) != null) {
//                    startActivity(intentTienda)
//                } else {
//                    Log.d("MIAPP_BUSQUEDA", "El dispositivo ni tiene navegador ni tiene Play Store")
//                }
//            }
        } catch (e: Throwable) {
            Log.d("MIAPP_BUSQUEDA", "Excepción no hay navegador")
        }


    }
}