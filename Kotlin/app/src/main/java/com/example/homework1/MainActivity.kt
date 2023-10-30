package com.example.homework1

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
/*class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeWork1Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                }
            }
        }
    }
}

fun MyScreen() {}
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp { MyScreenContent() }
        }
    }
}

@Composable
fun MyApp(content: @Composable () -> Unit) {
    MaterialTheme {
        Surface(color = Color.White) {
            content()
        }
    }
}

@Composable
fun MyScreenContent() {
    val numberOfColumns =
        if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT) 3 else 4
    val counter = rememberSaveable {
        mutableStateOf(0)
    }
    val numbers: MutableState<List<Int>> = rememberSaveable { mutableStateOf(listOf()) }
    val numbers2: MutableState<List<Int>> = rememberSaveable { mutableStateOf(listOf()) }
    val numbers3: MutableState<List<Int>> = rememberSaveable { mutableStateOf(listOf()) }
    if (numberOfColumns == 3) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.92f)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth(0.33f)
                        .fillMaxHeight(),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    itemsIndexed(
                        items = numbers.value,
                        key = { _, item -> item }
                    ) { index, number ->
                        SquareCard(number, index
                        )
                    }
                }
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth(0.66f)
                        .fillMaxHeight(),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    itemsIndexed(
                        items = numbers2.value,
                        key = { _, item -> item }
                    ) { index, number ->
                        SquareCard(number, index)
                    }
                }
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth(0.99f)
                        .fillMaxHeight(),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    itemsIndexed(
                        items = numbers3.value,
                        key = { _, item -> item }
                    ) { index, number ->
                        SquareCard(number, index)
                    }
                }
            }
        }
    }

    Row {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    counter.value++;
                    if (counter.value % 3 == 1)
                        numbers.value = numbers.value + (counter.value)
                    if (counter.value % 3 == 2)
                        numbers2.value = numbers2.value + (counter.value)
                    if (counter.value % 3 == 0)
                        numbers3.value = numbers3.value + (counter.value)
                },
            ) {
                Text(text = "Add")
            }
        }
    }
    /*LazyColumn(
        /*modifier = Modifier.fillMaxSize(), */
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.92f),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        itemsIndexed(
            items = numbers.value,
            key = { _, item -> item }
        ) { index, number ->
            SquareCard(number, index)
        }
    }*/
    /*Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Button(
            onClick = {
                numbers.value = numbers.value + (numbers.value.size + 1)
            },
        ) {
            Text(text = "Add")
        }
    }*/
}
@Composable
fun SquareCard(number: Int, index: Int) {
    val backgroundColor = if (index % 2 != 0) Color.Red else Color.Blue

    Box(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = number.toString(),
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApp { MyScreenContent() }
}