package com.example.culinarychest.domain.usecase.tokenUseCase

import com.example.culinarychest.domain.repository.TokenRepository

class GetTokenUseCase(
    private val repository: TokenRepository
) {
    operator fun invoke(): String?{
        return repository.getToken()
    }
}