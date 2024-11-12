package com.example.beercellarapp

sealed class NavRoutes(val route: String)
    data object BeerList : NavRoutes("beer list")