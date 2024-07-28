package com.example.culinarychest.domain.model

sealed class ProcessingResult<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T?) : ProcessingResult<T>(data)
    class Error<T>(message: String, data: T? = null) : ProcessingResult<T>(data, message)

    class Loading<T> : ProcessingResult<T>()
}

