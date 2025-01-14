package com.example.beercellarapp.repository

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.beercellarapp.models.Beer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BeersRepository {
    private val baseUrl = "https://anbo-restbeer.azurewebsites.net/api/"
    private val beerService: BeerService

    val beers: MutableState<List<Beer>> = mutableStateOf(listOf())
    val isLoadingBeers = mutableStateOf(false)
    val errorMessage = mutableStateOf("")

    init {
        val build: Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        beerService = build.create(BeerService::class.java)
        getBeers()
    }

    fun getBeers() {
        isLoadingBeers.value = true
        beerService.getAllBeers().enqueue(object : Callback<List<Beer>> {
            override fun onResponse(call: Call<List<Beer>>, response: Response<List<Beer>>) {
                isLoadingBeers.value = false
                if (response.isSuccessful) {
                    val beerList: List<Beer>? = response.body()
                    beers.value = beerList ?: emptyList()
                    errorMessage.value = ""
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessage.value = message
                    Log.d("APPLE", message)
                }
            }

            override fun onFailure(call: Call<List<Beer>>, t: Throwable) {
                isLoadingBeers.value = false
                val message = t.message ?: "No connection to back-end"
                errorMessage.value = message
                Log.d("APPLE", message)
            }
        })
    }

    fun add(beer: Beer) {
        beerService.saveBeer(beer).enqueue(object : Callback<Beer> {
            override fun onResponse(call: Call<Beer>, response: Response<Beer>) {
                if (response.isSuccessful) {
                    Log.d("APPLE", "Added " + response.body())
                    errorMessage.value = ""
                    getBeers()
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessage.value = message
                    Log.d("APPLE", message)
                }
            }

            override fun onFailure(call: Call<Beer>, t: Throwable) {
                val message = t.message ?: "No connection to back-end"
                errorMessage.value = message
                Log.d("APPLE", message)
            }
        })
    }

    fun delete(id: Int) {
        Log.d("APPLE", "Delete: $id")
        beerService.deleteBeer(id).enqueue(object : Callback<Beer> {
            override fun onResponse(call: Call<Beer>, response: Response<Beer>) {
                if (response.isSuccessful) {
                    Log.d("APPLE", "Deleted: " + response.body())
                    errorMessage.value = ""
                    getBeers()
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessage.value = message
                    Log.d("APPLE", "Not deleted: $message")
                }
            }

            override fun onFailure(call: Call<Beer>, t: Throwable) {
                val message = t.message ?: "No connection to back-end"
                errorMessage.value = message
                Log.d("APPLE", "Not Deleted: $message")
            }
        })
    }


    fun update(beerId: Int, beer: Beer) {
        beerService.updateBeer(beerId, beer).enqueue(object : Callback<Beer> {
            override fun onResponse(call: Call<Beer>, response: Response<Beer>) {
                if (response.isSuccessful) {
                    Log.d("APPLE", "Updated: " + response.body())
                    errorMessage.value = ""
                    getBeers()
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessage.value = message
                    Log.d("APPLE", "Update: $message")
                }
            }

            override fun onFailure(call: Call<Beer>, t: Throwable) {
                val message = t.message ?: "No connection to back-end"
                errorMessage.value = message
                Log.d("APPLE", "Update: $message")
            }
        })
    }

    fun sortBeersByName(ascending: Boolean) {
        Log.d("APPLE", "Sort by Name")
        if (ascending)
            beers.value = beers.value.sortedBy { it.name }
        else
            beers.value = beers.value.sortedByDescending { it.name }
    }

    fun sortBeersByHowMany(ascending: Boolean) {
        Log.d("APPLE", "Sort by Quantity")
        if (ascending)
            beers.value = beers.value.sortedBy { it.howMany }
        else
            beers.value = beers.value.sortedByDescending { it.howMany }
    }

    fun filterByName(titleFragment: String) {
        if (titleFragment.isEmpty()) {
            getBeers()
            return
        }
        beers.value =
            beers.value.filter {
                it.name.contains(titleFragment, ignoreCase = true)
            }
    }
}
















