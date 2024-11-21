package com.example.beercellarapp.screens

import android.content.res.Configuration
import android.icu.text.CaseMap.Title
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
import androidx.compose.ui.tooling.preview.Preview
import com.example.beercellarapp.models.Beer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BeerAdd(
    modifier: Modifier = Modifier,
    addBeer: (Beer) -> Unit = {},
    navigateBack: () -> Unit = {}
) {
    var brewery by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var style by remember { mutableStateOf("") }
    var abv by remember { mutableStateOf("") }
    var volume by remember { mutableStateOf("") }
    var pictureUrl by remember { mutableStateOf("") }
    var howMany by remember { mutableStateOf("") }

    // -||- IsError
    var breweryIsError by remember { mutableStateOf(false) }
    var nameIsError by remember { mutableStateOf(false) }
    var styleIsError by remember { mutableStateOf(false) }
    var abvIsError by remember { mutableStateOf(false) }
    var volumeIsError by remember { mutableStateOf(false) }
    var pictureUrlIsError by remember { mutableStateOf(false) }
    var howManyIsError by remember { mutableStateOf(false) }

    Scaffold(modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Add a beer") })
        }) { innerPadding ->
        Column(modifier = modifier.padding(innerPadding)) {
            val orientation = LocalConfiguration.current.orientation
            val isPortrait = orientation == Configuration.ORIENTATION_PORTRAIT
            if (isPortrait) {
                OutlinedTextField(
                    onValueChange = { brewery = it },
                    value = brewery,
                    isError = breweryIsError,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Brewery") }
                )
                OutlinedTextField(
                    onValueChange = { name = it },
                    value = name,
                    isError = nameIsError,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Name") }
                )
                OutlinedTextField(
                    onValueChange = { style = it },
                    value = style,
                    isError = styleIsError,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Style") }
                )
                OutlinedTextField(
                    onValueChange = { abv = it },
                    value = abv,
                    isError = abvIsError,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Alcohol by Volume") }
                )
                OutlinedTextField(
                    onValueChange = { volume = it },
                    value = volume,
                    isError = volumeIsError,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Volume") }
                )
                OutlinedTextField(
                    onValueChange = { pictureUrl = it },
                    value = pictureUrl,
                    isError = pictureUrlIsError,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Picture") }
                )
                OutlinedTextField(
                    onValueChange = { howMany = it },
                    value = howMany,
                    isError = howManyIsError,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Amount") }
                )
            } else {
                Row(
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    OutlinedTextField(
                        onValueChange = { brewery = it },
                        value = brewery,
                        isError = breweryIsError,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        modifier = Modifier.weight(1f),
                        label = { Text("Brewery") }
                    )
                    OutlinedTextField(
                        onValueChange = { name = it },
                        value = name,
                        isError = nameIsError,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        modifier = Modifier.weight(1f),
                        label = { Text("Name") }
                    )
                    OutlinedTextField(
                        onValueChange = { style = it },
                        value = style,
                        isError = styleIsError,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        modifier = Modifier.weight(1f),
                        label = { Text("Style") }
                    )
                    OutlinedTextField(
                        onValueChange = { abv = it },
                        value = abv,
                        isError = abvIsError,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f),
                        label = { Text("Alcohol by Volume") }
                    )
                    OutlinedTextField(
                        onValueChange = { volume = it },
                        value = volume,
                        isError = volumeIsError,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f),
                        label = { Text("Volume") }
                    )
                    OutlinedTextField(
                        onValueChange = { pictureUrl = it },
                        value = pictureUrl,
                        isError = pictureUrlIsError,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        modifier = Modifier.weight(1f),
                        label = { Text("Picture") }
                    )
                    OutlinedTextField(
                        onValueChange = { howMany = it },
                        value = howMany,
                        isError = howManyIsError,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        modifier = Modifier.weight(1f),
                        label = { Text("Amount") })
                }
            }
            Row(
                modifier = modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly
            )
            {
                Button(onClick = { navigateBack() }) {
                    Text("Back")
                }
                Button(onClick = {
                    if (name.isEmpty()) {
                        nameIsError = true
                        return@Button
                    }
                    val beer = Beer(
                        brewery = brewery,
                        name = name,
                        style = style,
                        abv = abv,
                        volume = volume,
                        pictureUrl = pictureUrl,
                        howMany = howMany,
                    )
                    addBeer(beer)
                    navigateBack()
                }
            }
        }
    }

    @Preview
    @Composable
    fun AddBeer() {
        BeerAdd()
    }