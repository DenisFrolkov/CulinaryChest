package com.example.culinarychest.domain.repository

interface ImageRepository {

    suspend fun getImage(imageId: String): String
}