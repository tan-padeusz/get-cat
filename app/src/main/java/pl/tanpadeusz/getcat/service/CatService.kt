package pl.tanpadeusz.getcat.service

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

object CatService {
    private interface CatInterface {
        @GET("images/search")
        suspend fun getImage(@Query("api_key") apiKey: String): Response<List<ImageResponse>>
    }

    private const val ApiKey = "live_IAoAB34U2EUpatowjmrCijlNfLRHV4kWIW1qyQzyZBIah4u51x65og2TGsUL3m8v"
    private const val BaseURL = "https://api.thecatapi.com/v1/"

    private val moshi : Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofit : Retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(this.moshi))
        .baseUrl(CatService.BaseURL)
        .build()

    private val service : CatInterface by lazy {
        this.retrofit.create(CatInterface::class.java)
    }

    suspend fun getImage(): Response<List<ImageResponse>> {
        return this.service.getImage(this.ApiKey)
    }
}