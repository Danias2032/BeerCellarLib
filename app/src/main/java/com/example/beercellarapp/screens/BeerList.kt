package com.example.beercellarapp.screens

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.beercellarapp.models.Beer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BeerList(
    beers: List<Beer>,
    errorMessage: String,
    modifier: Modifier = Modifier,
    onBeerSelected: (Beer) -> Unit = {},
    onBeerDeleted: (Beer) -> Unit = {},
    onBeerAdd: () -> Unit = {},
    sortByName: (up: Boolean) -> Unit = {},
    sortByHowMany: (up: Boolean) -> Unit = {},
    filterByName: (String) -> Unit = {}
) {
    Scaffold(modifier = modifier,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = { Text("Beer List") })
        },
        floatingActionButtonPosition = FabPosition.EndOverlay,
        floatingActionButton = {
            FloatingActionButton(
                shape = CircleShape,
                onClick = { onBeerAdd() },
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add")
            }
        }) { innerPadding ->
        BeerListPanel(
            beers = beers,
            modifier = Modifier.padding(innerPadding),
            errorMessage = errorMessage,
            onBeerSelected = onBeerSelected,
            onBeerDeleted = onBeerDeleted,
            onSortByName = sortByName,
            onSortByHowMany = sortByHowMany,
            onFilterByName = filterByName
        )
    }
}

@Composable
private fun BeerListPanel(
    beers: List<Beer>,
    modifier: Modifier = Modifier,
    errorMessage: String,
    onBeerSelected: (Beer) -> Unit,
    onBeerDeleted: (Beer) -> Unit,
    onSortByName: (up: Boolean) -> Unit,
    onSortByHowMany: (up: Boolean) -> Unit,
    onFilterByName: (String) -> Unit,
) {
    Column(modifier = modifier.padding(8.dp)) {
        if (errorMessage.isNotEmpty()) {
            Text(text = "Problem: $errorMessage")
        }
        val nameUp = "Name: \u2191"
        val nameDown = "Name: \u2193"
        val howManyUp = "How Many: \u2191"
        val howManyDown = "How Many: \u2193"
        var sortByNameAscending by remember { mutableStateOf(true) }
        var sortByHowManyAscending by remember { mutableStateOf(true) }
        var titleFragment by remember { mutableStateOf("") }

        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = titleFragment,
                onValueChange = { titleFragment = it },
                label = { Text("Filter by Name") },
                modifier = Modifier.weight(1f)
            )
            Button(
                onClick = { onFilterByName(titleFragment) },
                modifier = Modifier.padding(8.dp)
            ) {
                Text("Filter")
            }
        }

        Row {
            TextButton(onClick = {
                onSortByName(sortByNameAscending)
                sortByNameAscending = !sortByNameAscending
            })
            {
                Text(text = if (sortByNameAscending) nameDown else nameUp)
            }
            TextButton(onClick = {
                onSortByHowMany(sortByHowManyAscending)
                sortByHowManyAscending = !sortByHowManyAscending
            }) {
                Text(text = if (sortByHowManyAscending) howManyDown else howManyUp)
            }
        }
        val orientation = LocalConfiguration.current.orientation
        val columns = if (orientation == Configuration.ORIENTATION_PORTRAIT) 1 else 2

        LazyVerticalGrid(
            columns = GridCells.Fixed(columns),
        ) {
            items(beers) { beer ->
                BeerItem(
                    beer,
                    onBeerSelected = onBeerSelected,
                    onBeerDeleted = onBeerDeleted
                )
            }
        }
    }
}

@Composable
private fun BeerItem(
    beer: Beer,
    modifier: Modifier = Modifier,
    onBeerSelected: (Beer) -> Unit = {},
    onBeerDeleted: (Beer) -> Unit = {}
) {
    Card(modifier = modifier
        .padding(4.dp)
        .fillMaxSize(), onClick = { onBeerSelected(beer) })
    {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = beer.name + ": " + beer.howMany.toString()
            )
            Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = "Remove",
                modifier = Modifier
                    .padding(8.dp)
                    .clickable { onBeerDeleted(beer) }
            )
        }
    }
}

@Preview
@Composable
fun BeerListPreview() {
    BeerList(
        beers = listOf(
            Beer(0, "", "Guld dame", "", 0.0, 0.0, 10),
            Beer(1, "", "Dansk Pilsner", "", 0.0, 0.0, 8)
        ),
        errorMessage = "Some error message"
    )
}






















