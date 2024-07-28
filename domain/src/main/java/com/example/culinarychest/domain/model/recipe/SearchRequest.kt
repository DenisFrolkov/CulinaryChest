package com.example.culinarychest.domain.model.recipe

import com.example.culinarychest.domain.model.application_user.Token

data class SearchRequest(
    val token: Token,
    val searchTerm: String?
)
