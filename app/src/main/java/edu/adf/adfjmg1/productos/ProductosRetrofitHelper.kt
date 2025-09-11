package edu.adf.adfjmg1.productos

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ProductosRetrofitHelper {

    private const val URL_BASE = "https://my-json-server.typicode.com/"

    private val retrofit = Retrofit.Builder().baseUrl(URL_BASE)
        //.baseUrl("https://my-json-server.typicode.es")//para probar la excepción
        .addConverterFactory(GsonConverterFactory.create()) // esta es la clase que se va a encargar de serializar o deserializar
        .build()

    fun getProductoServiceInstance(): ProductoService
    {
        val productoService = retrofit.create(ProductoService::class.java)
        return productoService
    }

}