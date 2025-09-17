package edu.adf.adfjmg1.canciones

import retrofit2.http.GET
import retrofit2.http.Path

interface ITunesService {
//    @GET("search/?media=music&term={patronBusqueda}")
    @GET("search/?media=music&term=BISBAL")
//    suspend fun obtenerCanciones(@Path("patronBusqueda") patron:String): ResultITunes
    suspend fun obtenerCanciones(): ResultITunes

}