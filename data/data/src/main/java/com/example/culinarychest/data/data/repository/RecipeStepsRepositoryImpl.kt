package com.example.culinarychest.data.data.repository

import com.example.culinarychest.data.data.api.CulinaryChestAPI
import com.example.culinarychest.domain.domain.ProcessingResult
import com.example.culinarychest.domain.domain.dataclasses.Step
import com.example.culinarychest.domain.domain.interfaces.RecipeStepsRepository

class RecipeStepsRepositoryImpl(
    private val culinaryChestAPI: CulinaryChestAPI
) : RecipeStepsRepository {

    override suspend fun getRecipeSteps(): ProcessingResult<List<Step>> {
        return safeApiCall {
            culinaryChestAPI.getRecipeSteps()
        }
    }
    override suspend fun createRecipeStep(): ProcessingResult<Step> {
        return safeApiCall {
            culinaryChestAPI.createRecipeStep()
        }
    }
    override suspend fun updateRecipeStep(): ProcessingResult<Step> {
        return safeApiCall {
            culinaryChestAPI.updateRecipeStep()
        }
    }
    override suspend fun deleteRecipeStep(): ProcessingResult<Step> {
        return safeApiCall {
            culinaryChestAPI.deleteRecipeStep()
        }
    }

}