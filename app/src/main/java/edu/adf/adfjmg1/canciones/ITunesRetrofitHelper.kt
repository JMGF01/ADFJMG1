package edu.adf.adfjmg1.canciones

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ITunesRetrofitHelper {

    private const val URL_BASE = "https://itunes.apple.com"

    private val retrofit = Retrofit.Builder().baseUrl(URL_BASE).addConverterFactory(GsonConverterFactory.create()).build()

    fun getITunesServiceInstance(): ITunesService {
        val iTunesService = retrofit.create(ITunesService::class.java)
        return iTunesService
    }
}