package com.example.culinarychest.data.data.repository

import com.example.culinarychest.domain.domain.ProcessingResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

suspend fun <T : Any> safeApiCall(apiCall: suspend () -> T): Flow<ProcessingResult<T>> {
    return flow {
        try {
            val result = apiCall.invoke()
            emit(ProcessingResult.Success(result))
        } catch (e: IOException) {
            emit(ProcessingResult.Error(message = "Error loading recipes: ${e.message}"))
        } catch (e: HttpException) {
            emit(ProcessingResult.Error(message = "Error loading recipes: ${e.message}"))
        } catch (e: Exception) {
            emit(ProcessingResult.Error(message = "Error loading recipes: ${e.message}"))
        }
    }
}