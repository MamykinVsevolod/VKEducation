package com.example.test1

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateListOf

class MyViewModel: ViewModel() {
    val myList = mutableStateListOf<Int>()
}