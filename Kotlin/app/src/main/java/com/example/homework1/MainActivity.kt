package com.example.homework1

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import java.util.EventListener
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
    var isColorScreenVisible by remember { mutableStateOf(0) }
    val onSquareClick: (Int) -> Unit = { isColorScreenVisible = it }
    if (isColorScreenVisible == 0) {
        if (numberOfColumns == 3) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.92f)
            ) {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.92f)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(horizontal = 8.dp, vertical = 8.dp)
                                .fillMaxWidth(0.33f)
                                .fillMaxHeight()
                        ) {
                            numbers.value.forEachIndexed { index, number ->
                                SquareCard(number, index, 0, 1, onSquareClick)
                            }
                        }
                        Column(
                            modifier = Modifier
                                .padding(horizontal = 8.dp, vertical = 8.dp)
                                .fillMaxWidth(0.5f)
                                .fillMaxHeight()
                        ) {
                            numbers.value.forEachIndexed { index, number ->
                                SquareCard(number, index, 1, 1, onSquareClick)
                            }
                        }
                        Column(
                            modifier = Modifier
                                .padding(horizontal = 8.dp, vertical = 8.dp)
                                .fillMaxWidth(1f)
                                .fillMaxHeight()
                        ) {
                            numbers.value.forEachIndexed { index, number ->
                                SquareCard(number, index, 2, 1, onSquareClick)
                            }
                        }
                    }
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.85f)
            ) {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.92f)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(horizontal = 8.dp, vertical = 8.dp)
                                .fillMaxWidth(0.25f)
                                .fillMaxHeight()
                        ) {
                            numbers.value.forEachIndexed { index, number ->
                                SquareCard(number, index, 0, 2, onSquareClick)
                            }
                        }
                        Column(
                            modifier = Modifier
                                .padding(horizontal = 8.dp, vertical = 8.dp)
                                .fillMaxWidth(0.33f)
                                .fillMaxHeight()
                        ) {
                            numbers.value.forEachIndexed { index, number ->
                                SquareCard(number, index, 1, 2, onSquareClick)
                            }
                        }
                        Column(
                            modifier = Modifier
                                .padding(horizontal = 8.dp, vertical = 8.dp)
                                .fillMaxWidth(0.5f)
                                .fillMaxHeight()
                        ) {
                            numbers.value.forEachIndexed { index, number ->
                                SquareCard(number, index, 2, 2, onSquareClick)
                            }
                        }
                        Column(
                            modifier = Modifier
                                .padding(horizontal = 8.dp, vertical = 8.dp)
                                .fillMaxWidth(1f)
                                .fillMaxHeight()
                        ) {
                            numbers.value.forEachIndexed { index, number ->
                                SquareCard(number, index, 3, 2, onSquareClick)
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
                        numbers.value = numbers.value + (counter.value)
                    },
                ) {
                    Text(text = "Add")
                }
            }
        }
    } else {
        var currentColor = if (isColorScreenVisible % 2 == 0) Color.Red else Color.Blue
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = currentColor)
        ) {
            Text(
                text = isColorScreenVisible.toString(),
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxSize()
            )
        }
        BackHandler {
            isColorScreenVisible = 0
        }
    }
}

@Composable
fun SquareCard(number: Int, index: Int, numOfCulomns: Int, orient: Int, squareCardListener: (Int) -> Unit) {
    if (orient == 1) {
        if (index % 3 == numOfCulomns) {
            val backgroundColor = if (number % 2 == 0) Color.Red else Color.Blue
            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .size(100.dp, 100.dp)
                    .background(color = backgroundColor)
                    .clickable(onClick = {
                        squareCardListener(number)
                    })
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
                    .size(100.dp, 100.dp)
                    .background(color = backgroundColor)
                    .clickable(onClick = {
                        squareCardListener(number)
                    })
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
