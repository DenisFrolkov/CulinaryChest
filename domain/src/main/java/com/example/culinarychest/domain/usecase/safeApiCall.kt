package com.example.culinarychest.domain.usecase

import com.example.culinarychest.domain.model.ProcessingResult
import retrofit2.HttpException
import java.io.IOException

suspend fun <T> safeApiCall(apiCall: suspend () -> T) : ProcessingResult<T> {
    return try {
        ProcessingResult.Success(apiCall())
    } catch (e: HttpException) {
        ProcessingResult.Error(e.localizedMessage ?: "An unexpected error occurred")
    } catch (e: IOException) {
        ProcessingResult.Error("Couldn't reach server. Check your internet connection.")
    }
}