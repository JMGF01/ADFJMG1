package edu.adf.adfjmg1.canciones

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ITunesService {
//    @GET("search/?media=music&term={patronBusqueda}")
//    @GET("search/?media=music&term=BISBAL")
@GET("search?media=music")
//    suspend fun obtenerCanciones(@Path("patronBusqueda") patron:String): ResultITunes
//    suspend fun obtenerCanciones(): ResultITunes
    suspend fun obtenerCanciones(@Query("term") patronBusqueda: String): ResultITunes


}

