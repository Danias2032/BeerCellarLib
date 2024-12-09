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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.beercellarapp.models.AuthenticationViewModel
import com.example.beercellarapp.models.Beer
import com.example.beercellarapp.models.BeersViewModel
import com.example.beercellarapp.screens.Authentication
import com.example.beercellarapp.screens.BeerAdd
import com.example.beercellarapp.screens.BeerDetails
import com.example.beercellarapp.screens.BeerList
import com.example.beercellarapp.screens.Welcome
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
    val authenticationViewModel: AuthenticationViewModel = viewModel()
    val beers = viewModel.beers.value
    val errorMessage = viewModel.errorMessage.value

    NavHost(navController = navController, startDestination = NavRoutes.BeerList.route) {
        composable(NavRoutes.Authentication.route) {
            Authentication(
                user = authenticationViewModel.user,
                message = authenticationViewModel.message,
                signIn = { email, password -> authenticationViewModel.signIn(email, password) },
                register = { email, password -> authenticationViewModel.register(email, password) },
                navigateToNextScreen = { navController.navigate(NavRoutes.Welcome.route) }
            )
        }
        composable(NavRoutes.Welcome.route) {
            Welcome(
                user = authenticationViewModel.user,
                signOut = { authenticationViewModel.signOut() },
                navigateToAuthentication = {
                    navController.popBackStack(NavRoutes.Authentication.route, inclusive = false)
                }
            )
        }
        composable(NavRoutes.BeerList.route) {
            BeerList(
                beers = beers,
                errorMessage = errorMessage,
                modifier = modifier,
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
            val beer = beers.find { it.id == beerId } ?: Beer(
                1,
                "",
                name = "No Beer",
                "",
                0.0,
                0.0,
                howMany = 0
            )
            BeerDetails(
                beer = beer,
                modifier = modifier,
                onUpdate = { id: Int, beers: Beer -> viewModel.update(id, beers) },
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