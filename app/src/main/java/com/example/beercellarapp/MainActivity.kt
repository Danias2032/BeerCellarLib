package com.example.beercellarapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.beercellarapp.models.Beer
import com.example.beercellarapp.models.BeersViewModel
import com.example.beercellarapp.screens.BeerAdd
import com.example.beercellarapp.screens.BeerDetails
import com.example.beercellarapp.screens.BeerList
import com.example.beercellarapp.ui.theme.BeerCellarAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BeerCellarAppTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val viewModel: BeersViewModel = viewModel()
    val beers = viewModel.beers.value
    val errorMessage = viewModel.errorMessage.value

    NavHost(navController = navController, startDestination = NavRoutes.BeerList.route) {
        composable(NavRoutes.BeerList.route) {
            BeerList(
                modifier = modifier,
                beers = beers,
                errorMessage = errorMessage,
                onBeerSelected =
                { beer ->
                    navController.navigate(NavRoutes.BeerDetails.route + "/${beer.id}")
                },
                onBeerDeleted = { beer -> viewModel.remove(beer) },
                onBeerAdd = { navController.navigate(NavRoutes.BeerAdd.route) },
                sortByName = { viewModel.sortBeersByName(ascending = it) },
                sortByHowMany = { viewModel.sortBeersByHowMany(ascending = it) },
                filterByName = { viewModel.filterByName(it) }
            )
        }
        composable(
            NavRoutes.BeerDetails.route + "/{beerId}",
            arguments = listOf(navArgument("beerId") { type = NavType.IntType })
        ) { backStackEntry ->
            val beerId = backStackEntry.arguments?.getInt("beerId")
            val beer = beers.find { it.id == beerId } ?: Beer(1, "", name = "No Beer", "", 0.0, 0.0, howMany = 0)
            BeerDetails(modifier = modifier,
                beer = beer,
                onUpdate = { id: Int, beer: Beer -> viewModel.update(id, beer) },
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(NavRoutes.BeerAdd.route) {
            BeerAdd(modifier = modifier,
                addBeer = { beer -> viewModel.add(beer) },
                navigateBack = { navController.popBackStack() }
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun BeerListPreview() {
    BeerCellarAppTheme {
        MainScreen()
    }
}