package com.example.culinarychest.data.model

import com.example.culinarychest.data.model.application_user.DuplicationUserInfoDto
import com.example.culinarychest.data.model.application_user.LoginDto
import com.example.culinarychest.data.model.application_user.TokenDto
import com.example.culinarychest.data.model.application_user.UserDto
import com.example.culinarychest.data.model.application_user.UserInfoDto
import com.example.culinarychest.data.model.favorite_recipe.AddedDateFavoriteRecipeDto
import com.example.culinarychest.data.model.favorite_recipe.CreateFavoriteRecipeDto
import com.example.culinarychest.data.model.favorite_recipe.FavoriteRecipeDto
import com.example.culinarychest.data.model.recipe.RecipeDto
import com.example.culinarychest.data.model.step.StepDataDto
import com.example.culinarychest.data.model.step.StepDto
import com.example.culinarychest.domain.model.application_user.ApplicationUser
import com.example.culinarychest.domain.model.application_user.DuplicationUserInfo
import com.example.culinarychest.domain.model.application_user.Login
import com.example.culinarychest.domain.model.application_user.Token
import com.example.culinarychest.domain.model.application_user.UserInfo
import com.example.culinarychest.domain.model.favorite_recipe.AddedDateFavoriteRecipe
import com.example.culinarychest.domain.model.favorite_recipe.CreateFavoriteRecipe
import com.example.culinarychest.domain.model.favorite_recipe.FavoriteRecipe
import com.example.culinarychest.domain.model.recipe.Recipe
import com.example.culinarychest.domain.model.step.Step
import com.example.culinarychest.domain.model.step.StepData

object Mappers {

    fun ApplicationUser.toDto(): UserDto {
        return UserDto(
            userName = this.userName,
            email = this.email,
            password = this.password,
            roles = this.roles
        )
    }

    fun UserInfoDto.toDomain(): UserInfo {
        return UserInfo(
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

    fun DuplicationUserInfo.toDto(): DuplicationUserInfoDto {
        return DuplicationUserInfoDto(
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

    fun Token.toDto(): TokenDto {
        return TokenDto(
            token = this.token
        )
    }

    fun CreateFavoriteRecipe.toDto(): CreateFavoriteRecipeDto {
        return CreateFavoriteRecipeDto(
            token = this.token.toDto(),
            recipeId = this.recipeId,
            addedDate = this.addedDate.toDto()
        )
    }

    fun CreateFavoriteRecipeDto.toDomain(): CreateFavoriteRecipe {
        return CreateFavoriteRecipe(
            token = this.token.toDomain(),
            recipeId = this.recipeId,
            addedDate = this.addedDate.toDomain()
        )
    }

    fun AddedDateFavoriteRecipeDto.toDomain(): AddedDateFavoriteRecipe {
        return AddedDateFavoriteRecipe(
            addedDate = this.addedDate
        )
    }

    fun AddedDateFavoriteRecipe.toDto(): AddedDateFavoriteRecipeDto {
        return AddedDateFavoriteRecipeDto(
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

    fun StepData.toDto(): StepDataDto {
        return StepDataDto(
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