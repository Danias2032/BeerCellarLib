package com.example.beercellarapp.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import com.example.beercellarapp.models.Beer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BeerDetails(
    beer: Beer,
    modifier: Modifier = Modifier,
    onUpdate: (Int, Beer) -> Unit = { id: Int, data: Beer -> },
    onNavigateBack: () -> Unit = {}
) {
    var id by remember { mutableIntStateOf(beer.id) }
    var brewery by remember { mutableStateOf(beer.brewery) }
    var name by remember { mutableStateOf(beer.name) }
    var style by remember { mutableStateOf(beer.style) }
    var abv by remember { mutableDoubleStateOf(beer.abv) }
    var volume by remember { mutableDoubleStateOf(beer.volume) }
    var pictureUrl by remember { mutableStateOf(beer.pictureUrl) }
    var howMany by remember { mutableIntStateOf(beer.howMany) }

    Scaffold(modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Beer Details") })
        }) { innerPadding ->
        Column(modifier = modifier.padding(innerPadding)) {
            val orientation = LocalConfiguration.current.orientation
            val isPortrait = orientation == Configuration.ORIENTATION_PORTRAIT
            if (isPortrait) {
                OutlinedTextField(onValueChange = {
            }
        }
    }
}