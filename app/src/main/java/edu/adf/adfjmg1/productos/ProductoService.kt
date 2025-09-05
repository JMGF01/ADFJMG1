package edu.adf.adfjmg1.productos

import retrofit2.http.GET

interface ProductoService {

    @GET("miseon920/json-api/products")
    suspend fun obtenerProductos(): ListaProductos //se realiza en segundo plano mientras la actividad sigue operativa.

}