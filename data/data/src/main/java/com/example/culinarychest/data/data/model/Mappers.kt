package com.example.culinarychest.data.data.model

import com.example.culinarychest.data.data.model.application_user.ApplicationUserDto
import com.example.culinarychest.data.data.model.application_user.ApplicationUserInfoDto
import com.example.culinarychest.data.data.model.application_user.DuplicationUserInfoDto
import com.example.culinarychest.data.data.model.application_user.LoginDto
import com.example.culinarychest.data.data.model.application_user.TokenDto
import com.example.culinarychest.data.data.model.favorite_recipe.CreateFavoriteRecipeDto
import com.example.culinarychest.data.data.model.favorite_recipe.FavoriteRecipeDto
import com.example.culinarychest.data.data.model.recipe.CreateRecipeDto
import com.example.culinarychest.data.data.model.recipe.RecipeDto
import com.example.culinarychest.data.data.model.recipe.UpdateRecipeDto
import com.example.culinarychest.data.data.model.step.CreateStepDto
import com.example.culinarychest.data.data.model.step.StepDto
import com.example.culinarychest.data.data.model.step.UpdateStepDto
import com.example.culinarychest.domain.domain.model.application_user.ApplicationUser
import com.example.culinarychest.domain.domain.model.application_user.ApplicationUserInfo
import com.example.culinarychest.domain.domain.model.application_user.DuplicationUserInfo
import com.example.culinarychest.domain.domain.model.application_user.Login
import com.example.culinarychest.domain.domain.model.application_user.Token
import com.example.culinarychest.domain.domain.model.favorite_recipe.CreateFavoriteRecipe
import com.example.culinarychest.domain.domain.model.favorite_recipe.FavoriteRecipe
import com.example.culinarychest.domain.domain.model.recipe.CreateRecipe
import com.example.culinarychest.domain.domain.model.recipe.Recipe
import com.example.culinarychest.domain.domain.model.recipe.UpdateRecipe
import com.example.culinarychest.domain.domain.model.step.CreateStep
import com.example.culinarychest.domain.domain.model.step.Step
import com.example.culinarychest.domain.domain.model.step.UpdateStep

object Mappers {

    fun ApplicationUser.toDto(): ApplicationUserDto {
        return ApplicationUserDto(
            userName = this.userName,
            email = this.email,
            password = this.password,
            roles = this.roles
        )
    }

    fun ApplicationUserInfoDto.toDomain(): ApplicationUserInfo {
        return ApplicationUserInfo(
            id = this.id,
            userName = this.userName,
            email = this.email
        )
    }

    fun DuplicationUserInfoDto.toDomain(): DuplicationUserInfo {
        return DuplicationUserInfo(
            duplicateUserName = this.duplicateUserName, duplicateEmail = this.duplicateEmail
        )
    }

    fun Login.toDto(): LoginDto {
        return LoginDto(
            userName = this.userName,
            password = this.password
        )
    }

    fun TokenDto.toDomain(): Token {
        return Token(
            token = this.token
        )
    }

    fun CreateFavoriteRecipe.toDto(): CreateFavoriteRecipeDto {
        return CreateFavoriteRecipeDto(
            addedDate = this.addedDate
        )
    }

    fun FavoriteRecipeDto.toDomain(): FavoriteRecipe {
        return FavoriteRecipe(
            favoriteRecipeId = this.favoriteRecipeId,
            id = this.id,
            recipeId = this.recipeId,
            addedDate = this.addedDate
        )
    }


    fun RecipeDto.toDomain(): Recipe {
        return Recipe(
            recipeId = this.recipeId,
            id = this.id,
            imageUrl = this.imageUrl,
            title = this.title,
            ingredients = this.ingredients,
            steps = this.steps.map { it.toDomain() },
            creationDate = this.creationDate,
            preparationTime = this.preparationTime,
            savedCount = this.savedCount
        )
    }

    fun CreateStep.toDto(): CreateStepDto {
        return CreateStepDto(
            description = this.description,
            order = this.order
        )
    }

    fun StepDto.toDomain(): Step {
        return Step(
            description = this.description,
            order = this.order,
            recipeId = this.recipeId,
            stepId = this.stepId
        )
    }
}