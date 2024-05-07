package com.den.culinarychest.presentation.common.Button

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.den.culinarychest.presentation.ui.theme.SoftGray
import com.den.culinarychest.presentation.ui.theme.SoftOrange
import com.den.culinarychest.presentation.view_models.ApplicationUserRecipeViewModel
import com.example.culinarychest.data.data.TokenManager
import com.example.culinarychest.domain.domain.model.recipe.UpdateRecipe

@Composable
fun SaveButton(
    controller: NavController,
    navigationRoute: String,
    buttonText: String,
    colorButtonText: Color,
    recipeId: String,
    updateInfoRecipe: UpdateRecipe,
    applicationUserRecipeViewModel: ApplicationUserRecipeViewModel,
    tokenManager: TokenManager,
    buttonColor: Color,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .alpha(if (buttonColor == SoftOrange) 1f else 0.5f)
            .background(color = buttonColor, shape = RoundedCornerShape(12.dp))
            .border(width = 0.1.dp, color = SoftGray, shape = RoundedCornerShape(12.dp))
            .clip(shape = RoundedCornerShape(12.dp))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                tokenManager.getToken()?.let {
                    applicationUserRecipeViewModel.updateApplicationUserRecipe(
                        it, recipeId, updateInfoRecipe)
                }
                controller.navigate(navigationRoute)
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = buttonText,
            style = TextStyle(
                fontSize = 16.sp,
                color = colorButtonText
            ),
            modifier = Modifier.padding(vertical = 16.dp)
        )
    }
}