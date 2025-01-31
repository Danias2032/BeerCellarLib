package com.example.beercellarapp.screens

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
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
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            FloatingActionButton(
                shape = CircleShape,
                onClick = { onBeerAdd() }
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add Beer"
                )
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
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    val dark = isSystemInDarkTheme() //
    val color = if (dark) Color.Gray else Color.LightGray //

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

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.widthIn(max = 488.dp)
        ) {
            OutlinedTextField(
                value = titleFragment,
                onValueChange = { titleFragment = it },
                label = { Text("Filter by Name") },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = color, //
                    unfocusedContainerColor = Color.Unspecified //
                ),
                modifier = Modifier
                    .weight(1f)
                    .focusable()
            )
            Button(
                onClick = { onFilterByName(titleFragment) },
                modifier = Modifier
                    .padding(8.dp)
                    .focusable()
            ) {
                Text("Filter")
            }
        }

        Row {
            Button(onClick = {
                onSortByName(sortByNameAscending)
                sortByNameAscending = !sortByNameAscending
                focusManager.moveFocus(FocusDirection.Next) // Flytter fokus efter sortering.
            },
                modifier = Modifier
                    .padding(4.dp)
                    .semantics {
                        contentDescription = if (sortByNameAscending)
                            "Sort by name descending"
                        else
                            "Sort by name ascending"
                    })
            {
                Text(text = if (sortByNameAscending) nameDown else nameUp)
            }
            Button(
                onClick = {
                    onSortByHowMany(sortByHowManyAscending)
                    sortByHowManyAscending = !sortByHowManyAscending
                    focusManager.moveFocus(FocusDirection.Next)
                },
                modifier = Modifier
                    .padding(4.dp)
            )
            {
                Text(text = if (sortByHowManyAscending) howManyDown else howManyUp)
            }
        }
        val orientation = LocalConfiguration.current.orientation
        val columns = if (orientation == Configuration.ORIENTATION_PORTRAIT) 1 else 2
        val isToggled = remember { mutableStateOf(false) }

        Button(
            onClick = {
                focusRequester.requestFocus()
                isToggled.value = !isToggled.value
            },
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = if (isToggled.value)
                    "Focus first Beer"
                else
                    "Focus last Beer"
            )
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(columns),
        ) {
            items(beers) { beer ->
                BeerItem(
                    beer,
                    onBeerSelected = onBeerSelected,
                    onBeerDeleted = onBeerDeleted,
                    focusRequester = focusRequester
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
    onBeerDeleted: (Beer) -> Unit = {},
    focusRequester: FocusRequester
) {
    val focusManager = LocalFocusManager.current
    val isFocused = remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .focusable()
            .padding(4.dp)
            .fillMaxSize()
            .clickable(
                onClick = {
                    onBeerSelected(beer)
                    focusManager.moveFocus(FocusDirection.Down)
                },
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple()
            )
            .semantics {
                contentDescription =
                    "Beer: ${beer.name}, Quantity: ${beer.howMany}. Press to select."
            }
            .focusRequester(focusRequester)
            .onFocusChanged { focusState ->
                isFocused.value = focusState.isFocused
            }
            .background(
                if (isFocused.value)
                    Color.Blue
                else
                    Color.Transparent
            )
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier
                    .focusable()
                    .padding(8.dp)
                    .width(300.dp)
                    .align(Alignment.CenterStart),
                text = "${beer.name}: ${beer.howMany}",
            )
            Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = "Remove ${beer.name}. Press to delete.", // implenterer allerede semantics, sættes til null hvis icon er rent dekorativt.
                modifier = Modifier
                    .focusable()
                    .padding(12.dp)
                    .size(24.dp)
                    .align(Alignment.CenterEnd)
                    .clickable(
                        onClick = { showDialog = true },
                        interactionSource = remember { MutableInteractionSource() }, //
                        indication = rememberRipple() // effekt når man trykker på knappen.
                    )
            )
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Confirm deletion") },
            text = { Text("Are you sure you want to delete ${beer.name}?") },
            confirmButton = {
                TextButton(onClick = {
                    onBeerDeleted(beer)
                    showDialog = false
                },
                    modifier = Modifier
                        .semantics {
                            contentDescription = "Delete ${beer.name}"
                        } // semantics giver her knappen mulighed for uddybning.
                ) {
                    Text("Delete")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false },
                    modifier = Modifier
                        .semantics { contentDescription = "Cancel deletion" }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Preview
@Composable
fun BeerListPreview() {
    BeerList(
        beers = listOf(
            Beer(0, "", "Guld dame", "", 0.0, 0.0, 10),
            Beer(1, "", "Dansk Pilsner", "", 0.0, 0.0, 8),
            Beer(2, "", "Big Mash Up Barrel Aged Johnny Walker", "", 0.0, 0.0, 5)
        ),
        errorMessage = "Some error message"
    )
}






















