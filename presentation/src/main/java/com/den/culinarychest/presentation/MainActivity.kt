package com.den.culinarychest.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.den.culinarychest.presentation.ui.theme.CulinaryChestTheme
import com.den.culinarychest.presentation.view_models.ApplicationUserViewModel
import com.den.culinarychest.presentation.view_models.RecipeViewModel
import com.example.culinarychest.data.data.api.RetrofitInstance
import com.example.culinarychest.data.data.repository.ApplicationUserRepositoryImpl
import com.example.culinarychest.data.data.repository.RecipeRepositoryImpl
import com.example.culinarychest.domain.domain.dataclasses.Recipe
import kotlinx.coroutines.flow.collectLatest

class MainActivity : ComponentActivity() {

    private val recipeViewModel by viewModels<RecipeViewModel>(factoryProducer = {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return RecipeViewModel(RecipeRepositoryImpl(RetrofitInstance.culinaryChestApi))
                        as T
            }
        }
    }
    )

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CulinaryChestTheme {

                val username = "Denis123456"
                val email = "denis12345@mail.ru"
                val password = "1234567891234"
                val roles = listOf("User")

                Column {
                    Button(onClick = {
                        applicationUserViewModel.authorizeUser(username, password)
                    }) {

                    }

                    val recipeList = recipeViewModel.recipes.collectAsState().value

                    if (recipeList.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }} else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            contentPadding = PaddingValues(16.dp)
                        ) {
                            items(recipeList) { index ->
                                Recipe(index)
                                Spacer(modifier = Modifier.height(9.dp))
                            }
                        }
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
@Composable
fun Recipe(recipe: Recipe?) {
    recipe?.let { Text(text = it.title) }
}


