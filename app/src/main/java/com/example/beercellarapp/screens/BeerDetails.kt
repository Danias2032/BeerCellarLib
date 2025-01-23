package com.example.beercellarapp.screens

import android.content.res.Configuration
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.example.beercellarapp.models.Beer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BeerDetails(
    beer: Beer,
    modifier: Modifier = Modifier,
    onUpdate: (Int, Beer) -> Unit = { id: Int, data: Beer -> },
    onNavigateBack: () -> Unit = {}
) {
    var id by remember { mutableStateOf(beer.id.toString()) }
    var brewery by remember { mutableStateOf(beer.brewery) }
    var name by remember { mutableStateOf(beer.name) }
    var style by remember { mutableStateOf(beer.style) }
    var abvStr by remember { mutableStateOf(beer.abv.toString()) }
    var volumeStr by remember { mutableStateOf(beer.volume.toString()) }
    // var pictureUrl by remember { mutableStateOf(beer.pictureUrl.isNullOrEmpty()) }
    var howManyStr by remember { mutableStateOf(beer.howMany.toString()) }

    val dark = isSystemInDarkTheme()
    val color = if (dark) Color.Gray else Color.LightGray

    Scaffold(modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Beer Details") })
        }) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
        ) {
            val orientation = LocalConfiguration.current.orientation
            val isPortrait = orientation == Configuration.ORIENTATION_PORTRAIT
            if (isPortrait) {
                OutlinedTextField(
                    onValueChange = { id = it },
                    value = id,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = "Id") },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = color,
                        unfocusedContainerColor = Color.Unspecified
                    )
                )
                OutlinedTextField(
                    onValueChange = { brewery = it },
                    value = brewery,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = "Brewery") },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = color,
                        unfocusedContainerColor = Color.Unspecified
                    )
                )
                OutlinedTextField(
                    onValueChange = { name = it },
                    value = name,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = "Name") },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = color,
                        unfocusedContainerColor = Color.Unspecified
                    )
                )
                OutlinedTextField(
                    onValueChange = { style = it },
                    value = style,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = "Style") },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = color,
                        unfocusedContainerColor = Color.Unspecified
                    )
                )
                OutlinedTextField(
                    onValueChange = { abvStr = it },
                    value = abvStr,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = "Alcohol by Volume") },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = color,
                        unfocusedContainerColor = Color.Unspecified
                    )
                )
                OutlinedTextField(
                    onValueChange = { volumeStr = it },
                    value = volumeStr,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = "Volume") },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = color,
                        unfocusedContainerColor = Color.Unspecified
                    )
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
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = "Amount of Beers") },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = color,
                        unfocusedContainerColor = Color.Unspecified
                    )
                )
                Row(
                    modifier = modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(onClick = { onNavigateBack() }) {
                        Text("Back")
                    }
                    Button(onClick = {
                        val data = Beer(
                            id = id.toInt(),
                            brewery = brewery,
                            name = name,
                            style = style,
                            abvStr.toDouble(),
                            volumeStr.toDouble(),
                            howManyStr.toInt()
                        )
                        onUpdate(beer.id, data)
                        onNavigateBack()
                    }) {
                        Text("Update")
                    }
                }

            }
        }
    }
}

@Preview
@Composable
fun BeerDetailsPreview() {
    BeerDetails(
        beer = Beer(0, "", "", "", 0.0, 0.0, 0)
    )
}