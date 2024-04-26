package com.example.culinarychest.data.data.repository

import retrofit2.HttpException
import java.io.IOException

suspend fun <T : Any> safeApiCall(apiCall: suspend () -> T): com.example.culinarychest.domain.domain.ProcessingResult<T> {
    return try {
        com.example.culinarychest.domain.domain.ProcessingResult.Success(apiCall.invoke())
    } catch (e: IOException) {
        com.example.culinarychest.domain.domain.ProcessingResult.Error(message = "Error loading recipes: ${e.message}")
    } catch (e: HttpException) {
        com.example.culinarychest.domain.domain.ProcessingResult.Error(message = "Error loading recipes: ${e.message}")
    } catch (e: Exception) {
        com.example.culinarychest.domain.domain.ProcessingResult.Error(message = "Error loading recipes: ${e.message}")
    }
}