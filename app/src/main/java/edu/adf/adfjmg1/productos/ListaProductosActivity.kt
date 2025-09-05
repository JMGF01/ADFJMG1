package edu.adf.adfjmg1.productos

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import edu.adf.adfjmg1.Constantes
import edu.adf.adfjmg1.R
import edu.adf.adfjmg1.util.RedUtil
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ListaProductosActivity : AppCompatActivity() {

    lateinit var listaProductos: ListaProductos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_lista_productos)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // preparo Retrofit
        val retrofit = Retrofit.Builder().baseUrl("https://my-json-server.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create()) // esta es la clase que se va a encargar de serializar o deserializar
            .build()

        val productoService = retrofit.create(ProductoService::class.java)

        if (RedUtil.hayInternet(this))
        {
            Log.d(Constantes.ETIQUETA_LOG, "LANZANDO PETICIÓN HTTP0")
            lifecycleScope.launch { //corutina, lo que meta en el bloque se ejecuta en un segundo plano.
                Log.d(Constantes.ETIQUETA_LOG, "LANZANDO PETICIÓN HTTP1")
                listaProductos =  productoService.obtenerProductos()
                Log.d(Constantes.ETIQUETA_LOG, "RESPUESTA RX ...")
                listaProductos.forEach { Log.d(Constantes.ETIQUETA_LOG, it.toString())}
                // TODO HACER UN RECYCLER PARA MOSTRAR LA LISTA DE PRODUCTOS
            }
        } else
        {
            val noti = Toast.makeText(this, "SIN CONEXIÓN A INTERNET", Toast.LENGTH_LONG)
            noti.show()
        }
        Log.d(Constantes.ETIQUETA_LOG, "LANZANDO PETICIÓN HTTP2")


        /*
        SI HAY CONEXIÓN A INTERNET
            PIDO EL LISTADO DE PRODUCTOS
            DESPUÉS, MUESTRO EL LISTADO RX
         SI NO
            MUESTRO UN TOAST O MENSAJE DE ERROR
         */
    }
}