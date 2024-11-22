package com.example.beercellarapp.models

import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import com.example.beercellarapp.repository.BeersRepository

class BeersViewModel : ViewModel() {
    private val repository = BeersRepository()
    val beersFlow: State<List<Beer>> = repository.beersFlow
    val errorMessageFlow: State<String> = repository.errorMessageFlow
    val loadingFlow: State<Boolean> = repository.isLoadingBeers

    init {
        reload()
    }

    private fun reload() {
        repository.getBeers()
    }

    fun add(beer: Beer) {
        repository.add(beer)
    }
}