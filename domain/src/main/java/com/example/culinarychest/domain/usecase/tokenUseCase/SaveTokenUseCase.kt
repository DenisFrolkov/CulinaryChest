package com.example.culinarychest.domain.usecase.tokenUseCase

import com.example.culinarychest.domain.model.application_user.Token
import com.example.culinarychest.domain.repository.TokenRepository

class SaveTokenUseCase(
    private val repository: TokenRepository
){
    suspend operator fun invoke(token: Token) {
        return repository.saveToken(token)
    }
}