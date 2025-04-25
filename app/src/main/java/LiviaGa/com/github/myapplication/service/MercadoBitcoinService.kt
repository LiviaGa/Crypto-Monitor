package LiviaGa.com.github.myapplication.service

import retrofit2.http.GET
import retrofit2.Response
import LiviaGa.com.github.myapplication.model.TickerResponse


interface MercadoBitcoinService {

    @GET("api/BTC/ticker/")
    suspend fun getTicker(): Response<TickerResponse>

}