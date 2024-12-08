package com.example.beercellarapp.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.input.KeyboardType
import com.example.beercellarapp.models.Beer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BeerDetails(
    beer: Beer,
    modifier: Modifier = Modifier,
    onUpdate: (Int, Beer) -> Unit = { id: Int, data: Beer -> },
    onNavigateBack: () -> Unit = {}
) {
    var id by remember { mutableStateOf(beer.id) }
    var brewery by remember { mutableStateOf(beer.brewery) }
    var name by remember { mutableStateOf(beer.name) }
    var style by remember { mutableStateOf(beer.style) }
    var abvStr by remember { mutableStateOf(beer.abv.toString()) }
    var volumeStr by remember { mutableStateOf(beer.volume.toString()) }
    // var pictureUrl by remember { mutableStateOf(beer.pictureUrl.isNullOrEmpty()) }
    var howManyStr by remember { mutableStateOf(beer.howMany.toString()) }

    Scaffold(modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Beer Details") })
        }) { innerPadding ->
        Column(modifier = modifier.padding(innerPadding)) {
            val orientation = LocalConfiguration.current.orientation
            val isPortrait = orientation == Configuration.ORIENTATION_PORTRAIT
            if (isPortrait) {
                OutlinedTextField(
                    onValueChange = { brewery = it },
                    value = brewery,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = "Brewery") }
                )
                OutlinedTextField(
                    onValueChange = { name = it },
                    value = name,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = "Name") }
                )
                OutlinedTextField(
                    onValueChange = { style = it },
                    value = style,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = "Style") }
                )
                OutlinedTextField(
                    onValueChange = { abvStr = it },
                    value = abvStr,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = "Alcohol by Volume") }
                )
                OutlinedTextField(
                    onValueChange = { volumeStr = it },
                    value = volumeStr,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = "Volume") }
                )
                /*OutlinedTextField(
                    onValueChange = { pictureUrl = it },
                    value = pictureUrl,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = "Picture URL") })*/
                OutlinedTextField(
                    onValueChange = { howManyStr = it },
                    value = howManyStr,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = "Amount of Beers") }
                )
                Row(
                    modifier = modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(onClick = { onNavigateBack() }) {
                        Text("Back")
                    }
                }
            }
        }
    }
}