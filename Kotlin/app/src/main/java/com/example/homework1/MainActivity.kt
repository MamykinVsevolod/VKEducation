package com.example.homework1

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
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
fun Modifier.scrollEnabled(
    enabled: Boolean,
) = nestedScroll(
    connection = object : NestedScrollConnection {
        override fun onPreScroll(
            available: Offset,
            source: NestedScrollSource
        ): Offset = if(enabled) Offset.Zero else available
    }
)
var counting = 0
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
    val numbersL: MutableState<List<Int>> = rememberSaveable { mutableStateOf(listOf()) }
    val numbers2L: MutableState<List<Int>> = rememberSaveable { mutableStateOf(listOf()) }
    val numbers3L: MutableState<List<Int>> = rememberSaveable { mutableStateOf(listOf()) }
    val numbers4L: MutableState<List<Int>> = rememberSaveable { mutableStateOf(listOf()) }
    if (numberOfColumns == 3) {
        LazyColumn(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.92f)) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.92f)
                ) {
                    Column(
                        modifier = Modifier
                            .scrollEnabled(enabled = true) // To enable scrolling
                            .padding(horizontal = 8.dp, vertical = 8.dp)
                            .fillMaxWidth(0.33f)
                            .fillMaxHeight()
                    ) {
                        numbers.value.forEachIndexed { index, number ->
                            SquareCard(number, index, 0, 1)
                        }
                    }
                    Column(
                        modifier = Modifier
                            .scrollEnabled(enabled = true) // To enable scrolling
                            .padding(horizontal = 8.dp, vertical = 8.dp)
                            .fillMaxWidth(0.5f)
                            .fillMaxHeight()
                    ) {
                        numbers/*2*/.value.forEachIndexed { index, number ->
                            SquareCard(number, index, 1, 1)
                        }
                    }
                    Column(
                        modifier = Modifier
                            .scrollEnabled(enabled = true) // To enable scrolling
                            .padding(horizontal = 8.dp, vertical = 8.dp)
                            .fillMaxWidth(1f)
                            .fillMaxHeight()
                    ) {
                        numbers/*3*/.value.forEachIndexed { index, number ->
                            SquareCard(number, index, 2, 1)
                        }
                    }
                }
            }
        }
    } else {
        LazyColumn(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.92f)) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.92f)
                ) {
                    Column(
                        modifier = Modifier
                            .scrollEnabled(enabled = true) // To enable scrolling
                            .padding(horizontal = 8.dp, vertical = 8.dp)
                            .fillMaxWidth(0.25f)
                            .fillMaxHeight()
                    ) {
                        numbers.value.forEachIndexed { index, number ->
                            SquareCard(number, index, 0, 2)
                        }
                    }
                    Column(
                        modifier = Modifier
                            .scrollEnabled(enabled = true) // To enable scrolling
                            .padding(horizontal = 8.dp, vertical = 8.dp)
                            .fillMaxWidth(0.33f)
                            .fillMaxHeight()
                    ) {
                        numbers/*2*/.value.forEachIndexed { index, number ->
                            SquareCard(number, index, 1, 2)
                        }
                    }
                    Column(
                        modifier = Modifier
                            .scrollEnabled(enabled = true) // To enable scrolling
                            .padding(horizontal = 8.dp, vertical = 8.dp)
                            .fillMaxWidth(0.5f)
                            .fillMaxHeight()
                    ) {
                        numbers/*3*/.value.forEachIndexed { index, number ->
                            SquareCard(number, index, 2, 2)
                        }
                    }
                    Column(
                        modifier = Modifier
                            .scrollEnabled(enabled = true) // To enable scrolling
                            .padding(horizontal = 8.dp, vertical = 8.dp)
                            .fillMaxWidth(1f)
                            .fillMaxHeight()
                    ) {
                        numbers/*3*/.value.forEachIndexed { index, number ->
                            SquareCard(number, index, 3, 2)
                        }
                    }
                }
            }
        }
    }
    Row(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    counter.value++;
                    counting++;
                    /*if (counter.value % 3 == 1)
                        numbers.value = numbers.value + (counter.value)
                        numbers2.value = numbers2.value + (-1)
                        numbers3.value = numbers2.value + (-1)
                    if (counter.value % 3 == 2)
                        numbers2.value = numbers2.value + (counter.value)
                    numbers1.value = numbers2.value + (-1)
                    numbers3.value = numbers2.value + (-1)
                    if (counter.value % 3 == 0)
                        numbers3.value = numbers3.value + (counter.value)
                    numbers2.value = numbers2.value + (-1)
                    numbers1.value = numbers2.value + (-1)*/
                    numbers.value = numbers.value + (counter.value)
                },
            ) {
                Text(text = "Add")
            }
        }
    }
}

@Composable
fun SquareCard(number: Int, index: Int, numOfCulomns: Int, orient: Int) {
    if (orient == 1) {
        if (index % 3 == numOfCulomns) {
            val backgroundColor = if (number % 2 == 0) Color.Red else Color.Blue
            Box(
                modifier = Modifier
                    .padding(4.dp)
                    /*.fillMaxWidth()
                .fillMaxHeight()*/
                    .size(100.dp, 100.dp)
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
    } else {
        if (index % 4 == numOfCulomns) {
            val backgroundColor = if (number % 2 == 0) Color.Red else Color.Blue
            Box(
                modifier = Modifier
                    .padding(4.dp)
                    /*.fillMaxWidth()
                .fillMaxHeight()*/
                    .size(100.dp, 100.dp)
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
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApp { MyScreenContent() }
}
/*Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.92f)
                //.verticalScroll(ScrollState(0))
                .scrollable(
                    state = rememberScrollState(),
                    enabled = true,
                    orientation = Orientation.Vertical
                )
        ) { */
/* LazyColumn() {
     item {  }
 }*/
//Box(modifier = Modifier.fillMaxSize()) {
/*Row (
    modifier = Modifier
        .fillMaxSize()
        .scrollable(
            state = rememberScrollState(),
            enabled = true,
            orientation = Orientation.Vertical
        )
        .scrollEnabled(enabled = true)
){ */



/*LazyColumn(
                       /*modifier = Modifier
                           .fillMaxWidth(0.33f)
                           .fillMaxHeight(), */
                       /*.scrollEnabled(
                                   enabled = false)*/
                       //state = rememberLazyListState(),
                       userScrollEnabled = false,
                       contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                   ) {
                       itemsIndexed(
                           items = numbers.value,
                           key = { _, item -> item }
                       ) { index, number ->
                           SquareCard(number, index)
                       }
                   }
                   LazyColumn(
                       modifier = Modifier
                           .fillMaxWidth(0.66f)
                           .fillMaxHeight(),
                       //state = rememberLazyListState(),
                       userScrollEnabled = false,
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
                       //state = rememberLazyListState(),
                       userScrollEnabled = false,
                       contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                   ) {
                       itemsIndexed(
                           items = numbers3.value,
                           key = { _, item -> item }
                       ) { index, number ->
                           SquareCard(number, index)
                       }
                   } */



               /* LazyRow(modifier = Modifier.fillMaxSize()) {
                    item { LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth(0.33f)
                            .fillMaxHeight(),
                        /*.scrollEnabled(
                                    enabled = false)*/
                        //state = rememberLazyListState(),
                        userScrollEnabled = false,
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        itemsIndexed(
                            items = numbers.value,
                            key = { _, item -> item }
                        ) { index, number ->
                            SquareCard(number, index)
                        }
                    }
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth(0.66f)
                                .fillMaxHeight(),
                            //state = rememberLazyListState(),
                            userScrollEnabled = false,
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
                            //state = rememberLazyListState(),
                            userScrollEnabled = false,
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
                } */
                    /*LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth(0.2f)
                            .fillMaxHeight(),
                        /*.scrollEnabled(
                                    enabled = false)*/
                        //state = rememberLazyListState(),
                        userScrollEnabled = false,
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        itemsIndexed(
                            items = numbers.value,
                            key = { _, item -> item }
                        ) { index, number ->
                            SquareCard(number, index)
                        }
                    }
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth(0.4f)
                            .fillMaxHeight(),
                        //state = rememberLazyListState(),
                        userScrollEnabled = false,
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
                            .fillMaxWidth(0.6f)
                            .fillMaxHeight(),
                        //state = rememberLazyListState(),
                        userScrollEnabled = false,
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        itemsIndexed(
                            items = numbers3.value,
                            key = { _, item -> item }
                        ) { index, number ->
                            SquareCard(number, index)
                        }
                    } */
               // }
           // }


        //}

    /*Row {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    counter.value++;
                    counting++;
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
    } */
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
