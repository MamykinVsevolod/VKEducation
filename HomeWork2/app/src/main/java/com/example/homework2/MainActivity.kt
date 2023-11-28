package com.example.homework2

import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import androidx.compose.runtime.getValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import retrofit2.HttpException
import java.lang.Exception
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Random
fun generateRandomDate(): String {
    val startDate = LocalDate.of(2010, 1, 1) // начальная дата
    val endDate = LocalDate.of(2022, 12, 31) // конечная дата
    val random = Random()
    val randomDate = startDate.plusDays(random.nextInt((endDate.toEpochDay() - startDate.toEpochDay()).toInt())
        .toLong())
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    return randomDate.format(formatter)
}

class MainActivity : ComponentActivity() {
    private val apiKey = "SeV1GV7zfsDYS2WatsEFxekPPp5tCQnAHIWUo7So" // мой ключ API
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val connectivityManager = ContextCompat.getSystemService(
            this,
            ConnectivityManager::class.java
        ) as ConnectivityManager
        setContent {
            val isNetworkAvailable = remember {
                mutableStateOf(
                    connectivityManager.activeNetworkInfo?.isConnectedOrConnecting == true
                )
            }
                ImageScreen(apiKey, connectivityManager)
            //ImageScreen(apiKey)
        }
    }
}

data class ApodResponse(
    val url: String // Ссылка на изображение
)

interface ApiService {
    @GET("planetary/apod")
    suspend fun getApodImage(
       @Query("api_key") apiKey: String,
       @Query("date") date: String
    ): ApodResponse
}

object RetrofitClient {
    private const val BASE_URL = "https://api.nasa.gov/"
    val apiService: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

// @OptIn(DelicateCoroutinesApi::class)
@Composable
fun ImageScreen(apiKey: String, connectivityManager: ConnectivityManager) {
    val imageUrl: MutableState<List<String>> = rememberSaveable { mutableStateOf(listOf()) }
    val date: MutableState<List<String>> = rememberSaveable {
        mutableStateOf(listOf())
    }
    val counter = rememberSaveable {
        mutableStateOf(0)
    }
    val loading = remember { mutableStateOf(false) }
    val isNetworkAvailable = remember {
        mutableStateOf(
            connectivityManager.activeNetworkInfo?.isConnectedOrConnecting == true
        )
    }

    if (isNetworkAvailable.value) {
        if (counter.value != 0) {
            LaunchedEffect(counter.value) {
                try {
                    date.value += generateRandomDate()
                    val apodResponse = withContext(Dispatchers.IO) {
                        RetrofitClient.apiService.getApodImage(
                            apiKey,
                            date.value[date.value.size - 1]
                        )
                    }
                    imageUrl.value += apodResponse.url
                    loading.value = false
                    if (!imageUrl.value[imageUrl.value.size - 1].contains("apod.nasa.gov/apod")) {
                        loading.value = true
                    }
                    //Log.d("myTag", date.value[date.value.size - 1])
                   // Log.d("myTag", imageUrl.value[imageUrl.value.size - 1])
                } catch (e: HttpException) {
                    //Log.d("myTag", "HttpException")
                    loading.value = true
                } catch (e: Exception) {
                   // Log.d("myTag", "Exception")
                    loading.value = true
                }
            }
            if (imageUrl.value.size - 1 >= 0) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    val currentImageUrl = imageUrl.value[imageUrl.value.size - 1]
                    if (loading.value) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CircularProgressIndicator()
                            Text(text = "Ошибка загрузки. Нажмите кнопку...")
                        }
                    } else {
                        if (currentImageUrl.isNotEmpty()) {
                            BoxWithConstraints {
                                val maxWidth = maxWidth
                                val maxHeight = maxHeight
                                Image(
                                    painter =
                                    rememberAsyncImagePainter(
                                        ImageRequest.Builder(LocalContext.current)
                                            .data(data = currentImageUrl)
                                            .apply(block = fun ImageRequest.Builder.() {
                                                crossfade(true) // включаем плавное переключение между изображениями
                                            }).build()
                                    ),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .fillMaxSize(),
                                    contentScale = ContentScale.Fit
                                )
                            }
                        } else {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    } else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Проверьте ваше подключение к Интернету")
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
                    //Log.d("myTag", "counter: ${counter.value}")
                    isNetworkAvailable.value = connectivityManager.activeNetworkInfo?.isConnectedOrConnecting == true
                },
            ) {
                Text(text = "Получить изображение")
            }
        }
    }
}



/*@Composable
fun ImageScreen(apiKey: String) {
    var imageUrl by rememberSaveable { mutableStateOf("") }
    val date by rememberSaveable {
        mutableStateOf(generateRandomDate())
    }
    LaunchedEffect(Unit) {
        try {
            val apodResponse = withContext(Dispatchers.IO) {
                RetrofitClient.apiService.getApodImage(apiKey, date)
            }
            imageUrl = apodResponse.url
            Log.d("myTag", imageUrl)
        } catch (e: HttpException) {
            Log.d("myTag", "HttpException")
        } catch (e: Exception) {
            Log.d("myTag", "Exception")
        }
    }
    Log.d("myTag", generateRandomDate())

    // Отображение изображения
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (imageUrl.isNotEmpty()) {
            BoxWithConstraints {
                val maxWidth = maxWidth
                val maxHeight = maxHeight
                Image(
                    painter = // включаем плавное переключение между изображениями
                    rememberAsyncImagePainter(
                        ImageRequest.Builder(LocalContext.current).data(data = imageUrl).apply(block = fun ImageRequest.Builder.() {
                            crossfade(true) // включаем плавное переключение между изображениями
                        }).build()
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            }
        } else {
            CircularProgressIndicator()
        }
    }
}
*/

/*
 // Отображение изображения
    if (imageUrl.value.size - 1 >= 0) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.92f)
        ) {
            items(imageUrl.value.size) { index ->
                Log.d("myTag", "index: ${index}")
                Log.d("myTag", "imageUrl: ${imageUrl.value[index]}")
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    if (imageUrl.value[index].isNotEmpty()) {
                        BoxWithConstraints {
                            val maxWidth = maxWidth
                            val maxHeight = maxHeight
                            Image(
                                painter = // включаем плавное переключение между изображениями
                                rememberAsyncImagePainter(
                                    ImageRequest.Builder(LocalContext.current).data(data = imageUrl.value[index])
                                        .apply(block = fun ImageRequest.Builder.() {
                                            crossfade(true) // включаем плавное переключение между изображениями
                                        }).build()
                                ),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxSize(),
                                contentScale = ContentScale.Fit
                            )
                        }
                    } else {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
    */

