package edu.adf.adfjmg1.canciones

import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import edu.adf.adfjmg1.Constantes
import edu.adf.adfjmg1.R
import edu.adf.adfjmg1.databinding.ActivityCancionesBinding

class BusquedaCancionesActivity : AppCompatActivity() {

    lateinit var binding: ActivityCancionesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityCancionesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //this.binding.cajaBusqueda.requestFocus()
        this.binding.cajaBusqueda.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d(Constantes.ETIQUETA_LOG, "Buscando $query")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText?.isEmpty() ?: false) {
                    Log.d(Constantes.ETIQUETA_LOG, "CAJA LIMPIA")
                } else {
                    Log.d(Constantes.ETIQUETA_LOG, "Cambio $newText")
                }
                return true
            }

        })

    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.menu_canciones, menu)
//        val menuItemBusqueda = menu?.findItem(R.id.menuAPI_Canciones)
//        findViewById<SearchView>(R.id.menuAPI_Canciones)
//        menuI
//        searchView.queryHint = "Intro nombre o canción"
//        return true
//    }

}