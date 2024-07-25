package com.example.culinarychest.domain.usecase.tokenUseCase

import com.example.culinarychest.domain.repository.TokenRepository
import com.example.culinarychest.domain.repository.UserRepository

class SaveTokenUseCase(
    private val repository: TokenRepository
){
    operator fun invoke(token: String) {
        return repository.saveToken(token)
    }
}