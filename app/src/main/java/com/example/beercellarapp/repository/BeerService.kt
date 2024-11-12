package com.example.beercellarapp.repository

import com.example.beercellarapp.models.Beer
import retrofit2.Call
import retrofit2.http.GET

interface BeerService {
    @GET("beers")
    fun getAllBeers(): Call<List<Beer>>
}