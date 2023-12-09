package com.example.homework2

import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import coil.compose.AsyncImagePainter
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
            ImageScreen(apiKey, connectivityManager)
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
    val loading = rememberSaveable { mutableStateOf(false) }
    val isNetworkAvailable = rememberSaveable {
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
                    modifier = Modifier
                        .fillMaxSize(),
                    //contentAlignment = Alignment.Center
                ) {
                    val currentImageUrl = imageUrl.value[imageUrl.value.size - 1]
                    if (false) {
                        /*Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CircularProgressIndicator()
                            Text(text = "Ошибка загрузки. Нажмите кнопку...")
                        } */
                    } else {
                        Column (
                            modifier = Modifier.verticalScroll(rememberScrollState())
                                .align(Alignment.TopCenter)
                        ) {
                            for (item in imageUrl.value) {
                                if (item.isNotEmpty()) {
                                    Log.d("myTag", imageUrl.value[imageUrl.value.size - 1])
                                    BoxWithConstraints (
                                        modifier = Modifier
                                            .padding(10.dp)
                                            .size(250.dp, 250.dp)
                                            .clickable(onClick = {
                                                counter.value += 1;
                                            })
                                    ) {
                                        Image(
                                            painter =
                                            rememberAsyncImagePainter(
                                                ImageRequest.Builder(LocalContext.current)
                                                    .data(data = item)
                                                    .apply(block = fun ImageRequest.Builder.() {
                                                        crossfade(true) // включаем плавное переключение между изображениями
                                                    }).build()
                                            ),
                                            contentDescription = null,
                                            modifier = Modifier
                                                .fillMaxSize(),
                                            contentScale = ContentScale.Fit
                                        )
                                        /*val maxWidth = maxWidth
                                        val maxHeight = maxHeight
                                        val painter = rememberAsyncImagePainter(
                                            ImageRequest.Builder(LocalContext.current).data(data = item)
                                                .apply(block = fun ImageRequest.Builder.() {
                                                    crossfade(true) // включаем плавное переключение между изображениями
                                                }).build()
                                        )
                                        when(painter.state){
                                            AsyncImagePainter.State.Empty -> {}
                                            is AsyncImagePainter.State.Loading -> {
                                                CircularProgressIndicator()}
                                            is AsyncImagePainter.State.Success -> Image(
                                                painter = painter// включаем плавное переключение между изображениями
                                                ,
                                                contentDescription = null,
                                                modifier = Modifier
                                                    .fillMaxSize(),
                                                contentScale = ContentScale.Fit
                                            )
                                            is AsyncImagePainter.State.Error -> Text(text = (painter.state as AsyncImagePainter.State.Error).result.toString())
                                        } */
                                    }

                                } else {
                                    CircularProgressIndicator()
                                    Text(text = "Ошибка загрузки. Нажмите кнопку...")
                                }
                            }
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
/*
LaunchedEffect(counter.value) {
                try {
                    date.value += generateRandomDate()
                    Log.d("myTag", "date: ${date.value[date.value.size - 1]}")
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
                    Log.d("myTag", imageUrl.value.toString())
                    //Log.d("myTag", date.value[date.value.size - 1])
                   // Log.d("myTag", imageUrl.value[imageUrl.value.size - 1])
                } catch (e: HttpException) {
                    Log.d("myTag", "HttpException")
                    loading.value = true
                } catch (e: Exception) {
                    Log.d("myTag", e.message.toString())
                    loading.value = true
                }
            }
            Log.d("myTag", "index: ${imageUrl.value}")
            if (imageUrl.value.size - 1 >= 0) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.92f)
                ) {
                    items(imageUrl.value.size) { index ->

                        //Log.d("myTag", "index: ${index}")
                        Log.d("myTag", "imageUrl: ${imageUrl.value[index]}")
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            if (imageUrl.value[index].isNotEmpty()) {
                                BoxWithConstraints {
                                    val maxWidth = maxWidth
                                    val maxHeight = maxHeight
                                    val painter = rememberAsyncImagePainter(
                                        ImageRequest.Builder(LocalContext.current).data(data = imageUrl.value[index])
                                            .apply(block = fun ImageRequest.Builder.() {
                                                crossfade(true) // включаем плавное переключение между изображениями
                                            }).build()
                                    )
                                    when(painter.state){
                                        AsyncImagePainter.State.Empty -> {}
                                        is AsyncImagePainter.State.Loading -> {
                                            CircularProgressIndicator()}
                                        is AsyncImagePainter.State.Success -> Image(
                                            painter = painter// включаем плавное переключение между изображениями
                                            ,
                                            contentDescription = null,
                                            modifier = Modifier
                                                .fillMaxSize(),
                                            contentScale = ContentScale.Fit
                                        )
                                        is AsyncImagePainter.State.Error -> Text(text = (painter.state as AsyncImagePainter.State.Error).result.toString())
                                    }
                                }
                            } else {
                                CircularProgressIndicator()
                            }
                        }
                    }
                } */
