package com.example.culinarychest.domain.usecase.tokenUseCase

import com.example.culinarychest.domain.repository.TokenRepository

class ClearTokenUseCase(
    private val repository: TokenRepository
) {
    suspend operator fun invoke() {
        return repository.clearToken()
    }
}