package com.example.test1

import androidx.compose.foundation.layout.R
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration

class MainActivity : AppCompatActivity() {
    private val viewModel: MyViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var numbers = viewModel.myList
            Homework_1Theme {
                val columnCount = if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT) 3 else 4

                Surface(modifier = Modifier.fillMaxSize()) {

                    LazyColumn(Modifier.fillMaxSize()) {
                        item {
                            NumberListShow(numbers = numbers, columnCount = columnCount)
                        }
                        item{
                            AddNumberButton(numbers = numbers, getString(R.string.button_name))
                        }
                    }
                }
            }
        }
    }
}

