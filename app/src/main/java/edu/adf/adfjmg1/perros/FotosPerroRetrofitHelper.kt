package edu.adf.adfjmg1.perros

import edu.adf.adfjmg1.productos.ProductoService
import edu.adf.adfjmg1.productos.ProductosRetrofitHelper
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object FotosPerroRetrofitHelper
{
    private const val URL_FOTOS_BASE = "https://dog.ceo"
//    private const val URL_FOTOS_BASE = "https://dog.ceo/api/breed/<raza>/"

//    private val retrofit = Retrofit.Builder().baseUrl(URL_FOTOS_BASE).addConverterFactory(GsonConverterFactory.create()).build()

//    fun getFotosPerroServiceInstance(raza: String): FotosPerroService
//    {
//        val retrofit = Retrofit.Builder().baseUrl(URL_FOTOS_BASE.replace("<raza>",raza)).addConverterFactory(GsonConverterFactory.create()).build()
//
//        val fotoPerroService = retrofit.create(FotosPerroService::class.java)
//        return fotoPerroService
//    }

    fun getFotosPerroServiceInstance(): FotosPerroService
    {
        val retrofit = Retrofit.Builder().baseUrl(URL_FOTOS_BASE).addConverterFactory(GsonConverterFactory.create()).build()

        val fotoPerroService = retrofit.create(FotosPerroService::class.java)
        return fotoPerroService
    }
}