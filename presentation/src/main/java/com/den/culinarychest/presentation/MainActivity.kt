package com.den.culinarychest.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
import com.example.culinarychest.domain.domain.dataclasses.FavoriteRecipe
import com.example.culinarychest.domain.domain.dataclasses.Recipe
import com.example.culinarychest.domain.domain.dataclasses.Step

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
                    RecipeRepositoryImpl(RetrofitInstance.culinaryChestApi),
                    applicationUserViewModel = applicationUserViewModel
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CulinaryChestTheme {

                val username = "Denis12"
                val email = "denis12345@mail.ru"
                val password = "1234567891234"
                val roles = listOf("User")

                Column {


                    Button(onClick = {
                        applicationUserViewModel.authorizeUser(
                            username,
                            password
                        )
                    }) {

                    }
                    Button(onClick = {
                        applicationUserViewModel.token.observeForever { token ->
                            if (token != null) {
                                applicationUserRecipeViewModel.getApplicationUserRecipes(token)
                            }
                        }
                    }) {

                    }
                    val recipeList = applicationUserRecipeViewModel.applicationUserRecipes.collectAsState().value
                    LazyColumn {
                        items(recipeList) {recipe ->
                            Recipe(recipe)
                        }
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
        recipe.let { Text(text = it.title) }
        if (recipe.steps != null) {
            recipe.steps.forEach { step ->
                StepItem(step = step)
            }
        }
    }
}


@Composable
fun StepItem(step: Step) {
    Text(text = "${step.order}. ${step.description}")
}

@Composable
fun FavoriteRecipe(favoriteRecipe: FavoriteRecipe) {
    favoriteRecipe?.let { Text(text = it.id) }
}


