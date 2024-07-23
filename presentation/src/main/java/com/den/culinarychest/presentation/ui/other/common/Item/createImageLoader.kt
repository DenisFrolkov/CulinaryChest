package com.den.culinarychest.presentation.other.common.Item

import android.content.Context
import coil.ImageLoader
import com.example.culinarychest.data.data.api.RetrofitInstance
import com.example.culinarychest.data.data.repository.TokenManager

fun createImageLoader(context: Context): ImageLoader {
    val okHttpClient = RetrofitInstance(TokenManager(context)).okHttpClient
    return ImageLoader.Builder(context)
        .okHttpClient { okHttpClient }
        .build()
}