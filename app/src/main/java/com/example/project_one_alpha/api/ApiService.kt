package com.example.project_one_alpha.api

import com.example.project_one_alpha.api.datamodel.PokemonApi
import com.example.project_one_alpha.api.datamodel.PokemonListResponse
import com.example.project_one_alpha.db.datamodel.MoveType
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

const val BASE_URL = "https://pokeapi.co/api/v2/"



private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val loggin = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY

}

private val clint = OkHttpClient.Builder()
    .addInterceptor(loggin)
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .client(clint)
    .build()

interface ApiService {

    @GET("pokemon?limit=151")
    suspend fun getPokemonList(): PokemonListResponse

    @GET("pokemon/{pokemonName}")
    suspend fun getPokemonByName(@Path("pokemonName") pokemonName: String): PokemonApi

    @GET
    suspend fun getPokemonMove(@Url moveUrl: String): MoveType
}

object PokeApi {
    val retrofitService: ApiService by lazy { retrofit.create(ApiService::class.java) }

}