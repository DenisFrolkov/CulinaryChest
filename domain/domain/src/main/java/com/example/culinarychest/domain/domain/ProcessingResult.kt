package com.example.culinarychest.domain.domain

sealed class ProcessingResult<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T?) : ProcessingResult<T>(data)
    class Error<T>(data: T? = null, message: String) : ProcessingResult<T>(data, message)
}
