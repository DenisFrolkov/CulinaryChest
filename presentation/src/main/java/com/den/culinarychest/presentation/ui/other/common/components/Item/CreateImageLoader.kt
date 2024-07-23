package com.den.culinarychest.presentation.ui.other.common.components.Item

import android.content.Context
import coil.ImageLoader
import com.example.culinarychest.data.data.api.RetrofitInstance
import com.example.culinarychest.data.data.repository.TokenManager

fun CreateImageLoader(context: Context): ImageLoader {
    val okHttpClient = RetrofitInstance(TokenManager(context)).okHttpClient
    return ImageLoader.Builder(context)
        .okHttpClient { okHttpClient }
        .build()
}