package edu.adf.adfjmg1.perros

import edu.adf.adfjmg1.productos.ListaProductos
import retrofit2.http.GET
import retrofit2.http.Path

interface FotosPerroService {

    @GET("api/breed/{raza}/images")
//    @GET("images")
    suspend fun obtenerFotosPerro(@Path("raza") raza:String): FotosPerro
//    suspend fun obtenerFotosPerro(): FotosPerro

}
