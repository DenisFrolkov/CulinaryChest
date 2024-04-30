package com.den.culinarychest.presentation

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.den.culinarychest.presentation.ui.theme.CulinaryChestTheme
import com.den.culinarychest.presentation.view_models.ApplicationUserFavoriteRecipeViewModel
import com.den.culinarychest.presentation.view_models.ApplicationUserRecipeViewModel
import com.den.culinarychest.presentation.view_models.ApplicationUserViewModel
import com.den.culinarychest.presentation.view_models.RecipeViewModel
import com.example.culinarychest.data.data.api.RetrofitInstance
import com.example.culinarychest.data.data.repository.ApplicationUserFavoriteRecipeRepositoryImpl
import com.example.culinarychest.data.data.repository.ApplicationUserRecipeRepositoryImpl
import com.example.culinarychest.data.data.repository.ApplicationUserRepositoryImpl
import com.example.culinarychest.data.data.repository.RecipeRepositoryImpl
import com.example.culinarychest.domain.domain.dataclasses.ApplicationUserInfo
import com.example.culinarychest.domain.domain.dataclasses.favorite_recipe.FavoriteRecipe
import com.example.culinarychest.domain.domain.dataclasses.recipe.Recipe
import com.example.culinarychest.domain.domain.dataclasses.step.Step
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainActivity : ComponentActivity() {

    private val applicationUserViewModel by viewModels<ApplicationUserViewModel>(factoryProducer = {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ApplicationUserViewModel(
                    ApplicationUserRepositoryImpl(RetrofitInstance.culinaryChestApi)
                )
                        as T
            }
        }
    })

    private val recipeViewModel by viewModels<RecipeViewModel>(factoryProducer = {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return RecipeViewModel(
                    RecipeRepositoryImpl(RetrofitInstance.culinaryChestApi)
                )
                        as T
            }
        }
    })

    private val applicationUserFavoriteRecipeViewModel by viewModels<ApplicationUserFavoriteRecipeViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return ApplicationUserFavoriteRecipeViewModel(
                        ApplicationUserFavoriteRecipeRepositoryImpl(RetrofitInstance.culinaryChestApi),
                        applicationUserViewModel = applicationUserViewModel
                    )
                            as T
                }
            }
        }
    )

    private val applicationUserRecipeViewModel by viewModels<ApplicationUserRecipeViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return ApplicationUserRecipeViewModel(
                        ApplicationUserRecipeRepositoryImpl(RetrofitInstance.culinaryChestApi)
                    )
                            as T
                }
            }
        }
    )


    @SuppressLint("StateFlowValueCalledInComposition")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CulinaryChestTheme {

                val username = "Denis1214"
                val email = "124@mail.ru"
                val password = "1234567891234"
                val roles = listOf("User")


                val recipeId3 = "3"

                Column {
                    Button(onClick = {
                        applicationUserViewModel.authorizeUser(
                            username,
                            password
                        )
                    }) {

                    }
                }
            }
//                AppNavigation()
        }
    }

    @Composable
    private fun UserInfoText(userInfo: ApplicationUserInfo?) {
        userInfo?.let { Text(text = it.userName) }
    }
}


@Composable
fun Recipe(recipe: Recipe) {
    Column {
        Text(text = recipe.title)
        recipe.steps.forEach { step ->
            StepItem(step = step)
        }
    }
}


@Composable
fun StepItem(step: Step) {
    Text(text = "${step.order}. ${step.description}")
}

@Composable
fun FavoriteRecipe(favoriteRecipe: FavoriteRecipe) {
    favoriteRecipe?.let { it.id?.let { Text(text = it) } }
}

@RequiresApi(Build.VERSION_CODES.O)
fun getCurrentDateTime(): String {
    val currentDateTime = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS Z")
    return currentDateTime.format(formatter)
}

