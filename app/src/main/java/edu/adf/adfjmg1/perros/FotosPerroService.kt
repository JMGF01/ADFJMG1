package edu.adf.adfjmg1.perros

import edu.adf.adfjmg1.productos.ListaProductos
import retrofit2.http.GET

interface FotosPerroService {

//    @GET("api/breed/hound/images")
    @GET("images")
    suspend fun obtenerFotosPerro(): FotosPerro

}
