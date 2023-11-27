package com.example.homework2

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest2465
2465
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
        setContent {
            ImageScreen(apiKey)
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
            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current).data(data = imageUrl).apply(block = fun ImageRequest.Builder.() {
                        crossfade(true)
                    }).build()
                ),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        } else {
            CircularProgressIndicator()
        }
    }
}
