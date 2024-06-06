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

    //ApplicationUser
    fun ApplicationUser.toDto(): ApplicationUserDto {
        return ApplicationUserDto(
            userName = this.userName,
            email = this.email,
            password = this.password,
            roles = this.roles
        )
    }

    fun ApplicationUser.toDomain(): ApplicationUser {
        return ApplicationUser(
            userName = this.userName,
            email = this.email,
            password = this.password,
            roles = this.roles
        )
    }

    //ApplicationUserInfo
    fun ApplicationUserInfo.toDto(): ApplicationUserInfoDto {
        return ApplicationUserInfoDto(
            id = this.id,
            userName = this.userName,
            email = this.email
        )
    }

    fun ApplicationUserInfoDto.toDomain(): ApplicationUserInfo {
        return ApplicationUserInfo(
            id = this.id,
            userName = this.userName,
            email = this.email
        )
    }

    //DuplicationUserInfo
    fun DuplicationUserInfo.toDto(): DuplicationUserInfoDto {
        return DuplicationUserInfoDto(
            duplicateUserName = this.duplicateUserName,
            duplicateEmail = this.duplicateEmail
        )
    }

    fun DuplicationUserInfoDto.toDomain(): DuplicationUserInfo {
        return DuplicationUserInfo(
            duplicateUserName = this.duplicateUserName, duplicateEmail = this.duplicateEmail
        )
    }

    //Login
    fun Login.toDto(): LoginDto {
        return LoginDto(
            userName = this.userName,
            password = this.password
        )
    }

    fun LoginDto.toDomain(): Login {
        return Login(
            userName = this.userName,
            password = this.password
        )
    }

    //Token
    fun Token.toDto(): TokenDto {
        return TokenDto(
            token = this.token
        )
    }

    fun TokenDto.toDomain(): Token {
        return Token(
            token = this.token
        )
    }

    //CreateFavoriteRecipe
    fun CreateFavoriteRecipe.toDto(): CreateFavoriteRecipeDto {
        return CreateFavoriteRecipeDto(
            addedDate = this.addedDate
        )
    }

    fun CreateFavoriteRecipeDto.toDomain(): CreateFavoriteRecipe {
        return CreateFavoriteRecipe(
            addedDate = this.addedDate
        )
    }

    //FavoriteRecipe
    fun FavoriteRecipe.toDto(): FavoriteRecipeDto {
        return FavoriteRecipeDto(
            favoriteRecipeId = this.favoriteRecipeId,
            id = this.id,
            recipeId = this.recipeId,
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

    //CreateRecipe
    fun CreateRecipe.toDto(): CreateRecipeDto {
        return CreateRecipeDto(
            title = this.title,
            recipeImage = this.recipeImage,
            ingredients = this.ingredients,
            steps = this.steps.map { it.toDto() },
            creationDate = this.creationDate,
            preparationTime = this.preparationTime
        )
    }

    fun CreateRecipeDto.toDomain(): CreateRecipe {
        return CreateRecipe(
            title = this.title,
            recipeImage = this.recipeImage,
            ingredients = this.ingredients,
            steps = this.steps.map { it.toDomain() },
            creationDate = this.creationDate,
            preparationTime = this.preparationTime
        )
    }

    //Recipe
    fun Recipe.toDto(): RecipeDto {
        return RecipeDto(
            recipeId = this.recipeId,
            id = this.id,
            imageUrl = this.imageUrl,
            title = this.title,
            ingredients = this.ingredients,
            steps = this.steps as List<StepDto>,
            creationDate = this.creationDate,
            preparationTime = this.preparationTime,
            savedCount = this.savedCount
        )
    }

    fun RecipeDto.toDomain(): Recipe {
        return Recipe(
            recipeId = this.recipeId,
            id = this.id,
            imageUrl = this.imageUrl,
            title = this.title,
            ingredients = this.ingredients,
            steps = this.steps as List<Step>,
            creationDate = this.creationDate,
            preparationTime = this.preparationTime,
            savedCount = this.savedCount
        )
    }

    //UpdateRecipe
    fun UpdateRecipe.toDto(): UpdateRecipeDto {
        return UpdateRecipeDto(
//            imageUrl = this.imageUrl,
            title = this.title,
            ingredients = this.ingredients,
            creationDate = this.creationDate,
            preparationTime = this.preparationTime,
            savedCount = this.savedCount
        )
    }
    fun UpdateRecipeDto.toDomain(): UpdateRecipe {
        return UpdateRecipe(
//            imageUrl = this.imageUrl,
            title = this.title,
            ingredients = this.ingredients,
            creationDate = this.creationDate,
            preparationTime = this.preparationTime,
            savedCount = this.savedCount
        )
    }

    //CreateStep
    fun CreateStep.toDto(): CreateStepDto {
        return CreateStepDto(
            description = this.description,
            order = this.order
        )
    }
    fun CreateStepDto.toDomain(): CreateStep {
        return CreateStep(
            description = this.description,
            order = this.order
        )
    }

    //CreateStep
    fun Step.toDto(): StepDto {
        return StepDto(
            description = this.description,
            order = this.order,
            recipeId = this.recipeId,
            stepId = this.stepId
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

    //UpdateStep
    fun UpdateStep.toDto(): UpdateStepDto {
        return UpdateStepDto(
            stepId = this.stepId,
            description = this.description,
            order = this.order
        )
    }
    fun UpdateStepDto.toDomain(): UpdateStep {
        return UpdateStep(
            stepId = this.stepId,
            description = this.description,
            order = this.order
        )
    }

}