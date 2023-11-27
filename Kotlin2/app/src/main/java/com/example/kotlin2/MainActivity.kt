package com.example.kotlin2

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.io.IOException
import androidx.compose.runtime.getValue
import coil.Coil
import coil.imageLoader
import coil.request.ImageRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.asImageBitmap
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.compose.runtime.produceState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}

data class Trail(
    val name: String,
    val imageUrl: String
)

fun loadTrails(): List<Trail> {
    // Создаем клиента OkHttp
    val client = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    //Создание сервиса Retrofit
    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.chucknorris.io/jokes/random")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)

    //Выполнение запроса к API
    val response = retrofit.getTrails().execute()

    //Обработка ответа
    if (response.isSuccessful) {
        return response.body()?.trails.orEmpty()
    } else {
        throw IOException("Error loading trails: ${response.code()} " +
                "${response.message()}")
    }
}

interface ApiService {
    @GET("https://api.chucknorris.io/jokes/random")
    fun getTrails(): ():
}

data class ApiResponce(
    val trails: List<Trail>
)

@Composable
fun TrailTile(trail: Trail) {
    /*
    //Загрузка изображения с использованием Coil
    val context = LocalContext.current
    val image = remember {
        mutableStateOf<Drawable?>(null)
    }
    val request = ImageRequest.Builder(context)
        .data(trail.imageUrl)
        .build()
    LaunchedEffect(trail.imageUrl) {
        val drawable = withContext(Dispatchers.IO) {
            context.imageLoader.execute(request).drawable
        }
        image.value = drawable
    }

    //Отображаем плашки с изображением
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(16.dp)
            .shadow(4.dp, RoundedCornerShape(8.dp))
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            image.value?.let {
                if (bitmap == null) {
                    bitmap = (it as BitmapDrawable).bitmap
                }
                bitmap?.let {
                    Image(
                        bitmap = bitmap!!.asImageBitmap(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }

            }
        }
    } */
    // Загрузка изображения с использованием Coil
    val image = remember { mutableStateOf<Bitmap?>(null) }
    val context = LocalContext.current
    val request = ImageRequest.Builder(context)
        .data(trail.imageUrl)
        .build()

    LaunchedEffect(trail.imageUrl) {
        val drawable = context.imageLoader.execute(request).drawable
        image.value = (drawable as BitmapDrawable).bitmap
    }

    Card (shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(8.dp)
            .shadow(4.dp, RoundedCornerShape(8.dp))){
        Box(modifier = Modifier.fillMaxSize()) {
            image.value?.let {
                bitmap -> Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

    }
}

// Главный экран приложения, который будет отображать загрузку данных
// и список плашек с изображениями
@Composable
fun MainScreen() {
    // Состояние загрузки данных
    /*val loadingState = remember {
        mutableStateOf(true)
    }

    // Загрузка данных
    val trails = remember {
        mutableListOf<Trail>()
    }

    LaunchedEffect(Unit) {
        try {
            val result = withContext(Dispatchers.IO) {
                loadTrails()
            }
            trails.addAll(result)
            loadingState.value = false
        } catch (e: IOException) {
            Log.e("MainScreen", "Error loading trails: ${e.message}")
            loadingState.value = false
        }
    }

    // Отображаем состояние загрузки
    if (loadingState.value) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
            ) {
            CircularProgressIndicator()
        }
    } else {
        // Отображение списка плашек с изображениями
        LazyColumn(contentPadding = PaddingValues(16.dp)) {
            items(trails) {
                trail -> TrailTile(trail = trail)
            }
        }
    } */
    val trails by produceState(initialValue = emptyList()) {
        value = withContext(Dispatchers.IO) {
            loadTrails()
        }
    }

    LazyColumn(contentPadding = PaddingValues(16.dp)) {
        items(trails) {
            trail -> TrailTile(trail = trail)
        }
    }
}
