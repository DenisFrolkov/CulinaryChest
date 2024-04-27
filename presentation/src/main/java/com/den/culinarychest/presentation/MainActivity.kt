package com.den.culinarychest.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.den.culinarychest.presentation.ui.theme.CulinaryChestTheme
import com.den.culinarychest.presentation.view_models.ApplicationUserViewModel
import com.den.culinarychest.presentation.view_models.RecipeViewModel
import com.example.culinarychest.data.data.api.RetrofitInstance
import com.example.culinarychest.data.data.repository.ApplicationUserRepositoryImpl
import com.example.culinarychest.data.data.repository.RecipeRepositoryImpl
import kotlinx.coroutines.flow.collectLatest

private const val s = "Denis123456"

class MainActivity : ComponentActivity() {

    private val recipeViewModel by viewModels<RecipeViewModel>(factoryProducer = {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return RecipeViewModel(RecipeRepositoryImpl(RetrofitInstance.culinaryChestApi))
                        as T
            }
        }
    })

    private val applicationUserViewModel by viewModels<ApplicationUserViewModel>(factoryProducer = {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ApplicationUserViewModel(
                    ApplicationUserRepositoryImpl(
                        RetrofitInstance.culinaryChestApi
                    ),
                    recipeViewModel
                )
                        as T
            }
        }
    })

    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CulinaryChestTheme {

                val username = "Denis123456"
                val email = "denis12345@mail.ru"
                val password = "1234567891234"
                val roles = listOf("User")

                applicationUserViewModel.authorizeUser(username, password)

                val recipeList = recipeViewModel.recipes.collectAsState().value
                val context = LocalContext.current
                LaunchedEffect(key1 = recipeViewModel.showErrorToastChannel)
                {
                    recipeViewModel.showErrorToastChannel.collectLatest { show ->
                        if (show) {
                            Toast.makeText( context, "Error", Toast.LENGTH_SHORT )
                                .show()
                        }
                    }
                }

                LazyColumn {
                    items(recipeList) { recipe ->
                        Text(text = recipe.title, color = Color.Black)
                    }
                }


//                Button(onClick = { applicationUserViewModel.registerApplicationUser(username, email, password, roles) }) {
//
//                }
//                AppNavigation()
            }
        }
    }
}