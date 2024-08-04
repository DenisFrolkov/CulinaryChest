package com.example.culinarychest.data.repository

import com.example.culinarychest.domain.repository.ImageRepository

class ImageRepositoryImpl : ImageRepository {
    override suspend fun getImage(imageId: String): String {
        return "https://zany-meme-jp7rjw5xjwpfpv47-7286.app.github.dev/images/${imageId}"
    }
}