package com.example.beercellarapp

sealed class NavRoutes(val route: String) {
    data object Authentication : NavRoutes("authentication")
    data object Welcome : NavRoutes("welcome")
    data object BeerList : NavRoutes("list")
    data object BeerDetails : NavRoutes("details")
    data object BeerAdd : NavRoutes("add")
}