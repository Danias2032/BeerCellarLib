package com.example.beercellarapp.models

import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import com.example.beercellarapp.repository.BeersRepository

class BeersViewModel : ViewModel() {
    private val repository = BeersRepository()
    val beers: State<List<Beer>> = repository.beers
    val errorMessage: State<String> = repository.errorMessage
    val loading: State<Boolean> = repository.isLoadingBeers

    init {
        reload()
    }

    private fun reload() {
        repository.getBeers()
    }

    fun add(beer: Beer) {
        repository.add(beer)
    }

    fun update(beerId : Int, beer: Beer){
        repository.update(beerId, beer)
    }

    fun remove(beer: Beer) {
        repository.delete(beer.id)
    }

    fun sortBeersByName(ascending: Boolean) {
        repository.sortBeersByName(ascending)
    }

    fun sortBeersByHowMany(ascending: Boolean) {
        repository.sortBeersByHowMany(ascending)
    }

    fun filterByName(titleFragment: String) {
        repository.filterByName(titleFragment)
    }
}