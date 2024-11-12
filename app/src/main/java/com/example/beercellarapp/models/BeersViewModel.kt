package com.example.beercellarapp.models

import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import com.example.beercellarapp.repository.BeersRepository

class BeersViewModel: ViewModel() {
    private val repository = BeersRepository()
    val beersFlow: State<List<Beer>> = repository.beersFlow
    val errorMessageFlow: State<String> = repository.errorMessageFlow
    val reloadingFlow: State<Boolean> = repository.isLoadingBeers

    init {
        reload()
    }

    fun reload(){
        repository.getBeers()
    }
}