package com.example.culinarychest.domain.usecase

import com.example.culinarychest.domain.repository.ImageRepository

class GetRecipePhotoUseCase(private val imageRepository: ImageRepository) {
    suspend operator fun invoke(imageId: String): String {
        return imageRepository.getImage(imageId)
    }
}