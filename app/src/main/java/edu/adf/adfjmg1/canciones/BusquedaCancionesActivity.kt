package edu.adf.adfjmg1.canciones

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.adf.adfjmg1.Constantes
import edu.adf.adfjmg1.R
import edu.adf.adfjmg1.databinding.ActivityCancionesBinding
import edu.adf.adfjmg1.productos.ProductosAdapter
import edu.adf.adfjmg1.util.RedUtil
import kotlinx.coroutines.launch

class BusquedaCancionesActivity : AppCompatActivity() {

    lateinit var binding: ActivityCancionesBinding
    lateinit var adapter: CancionesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityCancionesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //this.binding.cajaBusqueda.requestFocus()
        this.binding.cajaBusqueda.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d(Constantes.ETIQUETA_LOG, "Buscando $query")

//                if (RedUtil.hayInternet(this@BusquedaCancionesActivity)){
                if (comprobarInternet(this@BusquedaCancionesActivity)){
                    Log.d(Constantes.ETIQUETA_LOG, "HAY INTERNET")

                    lifecycleScope.launch {
                        val iTunesService = ITunesRetrofitHelper.getITunesServiceInstance()
                        Log.d(Constantes.ETIQUETA_LOG, "LANZANDO PETICIÓN HTTP")
//                        val resultITunes: ResultITunes = iTunesService.obtenerCanciones(query ?: "")
                        val resultITunes: ResultITunes = iTunesService.obtenerCanciones()
                        mostrarListadoCanciones(resultITunes)
                    }
                }
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

    fun comprobarInternet(contexto: Context): Boolean
    {
       return RedUtil.hayInternet(contexto)
    }

    fun mostrarListadoCanciones(resultITunes: ResultITunes) {
        this.adapter = CancionesAdapter(resultITunes)
        this.binding.recViewCanciones.adapter = this.adapter
        this.binding.recViewCanciones.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
    }

}