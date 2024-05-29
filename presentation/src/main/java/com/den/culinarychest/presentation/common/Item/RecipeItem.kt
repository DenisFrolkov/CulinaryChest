package com.den.culinarychest.presentation.common.Item

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import com.den.culinarychest.R
import com.den.culinarychest.presentation.ui.theme.SoftGray
import com.den.culinarychest.presentation.ui.theme.SoftOrange
import com.den.culinarychest.presentation.view_models.ApplicationUserFavoriteRecipeViewModel
import com.example.culinarychest.data.data.repository.TokenManager
import com.example.culinarychest.domain.domain.model.recipe.Recipe
import okhttp3.OkHttpClient
import java.security.cert.X509Certificate
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

@Composable
fun RecipeItem(
    controller: NavController,
    textRouteNavigation: String,
    recipe: Recipe,
    tokenManager: TokenManager,
    applicationUserFavoriteRecipeViewModel: ApplicationUserFavoriteRecipeViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
            .clickable {
                tokenManager
                    .getToken()
                    ?.let {
                        applicationUserFavoriteRecipeViewModel.getFavoriteRecipeByRecipeId(
                            it,
                            recipe.recipeId
                        )
                    }
                controller.navigate("${textRouteNavigation}/${recipe.recipeId}")
            }
            .border(width = .15.dp, color = SoftGray, shape = RoundedCornerShape(12.dp))
            .background(SoftOrange, RoundedCornerShape(12.dp))
    ) {
        Row {
            val desiredPath = recipe.imageUrl.substringAfter("/wwwroot/")
            val imageUrl = "https://10.0.2.2:7286/${desiredPath}"
            LoadImage(
                LocalContext.current,
                imageUrl = imageUrl
            )
            Column(
                modifier = Modifier.padding(start = 10.dp, top = 12.dp)
            ) {
                Text(
                    text = imageUrl,
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = SoftGray
                    )
                )
                Text(
                    text = recipe.ingredients,
                    style = TextStyle(
                        fontSize = 12.sp,
                        color = SoftGray
                    ),
                    modifier = Modifier.padding(top = 4.dp),
                )
            }
        }
        Row(
            modifier = Modifier.padding(start = 23.dp, top = 6.dp, end = 8.dp, bottom = 4.dp)
        ) {
            DisplayRecipeInfo(
                iconRecipeInfo = painterResource(id = R.drawable.recipe_info_star_icon),
                sizeRecipeInfoIcon = 20,
                textRecipeInfo = "${recipe.savedCount}",
                textFontSize = 12
            )
            Spacer(modifier = Modifier.width(8.dp))
            DisplayRecipeInfo(
                iconRecipeInfo = painterResource(id = R.drawable.recipe_info_time_icon),
                sizeRecipeInfoIcon = 20,
                textRecipeInfo = recipe.preparationTime,
                textFontSize = 12
            )
            Box(
                contentAlignment = Alignment.CenterEnd,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                DisplayRecipeInfo(
                    iconRecipeInfo = painterResource(id = R.drawable.recipe_info_calendar_icon),
                    sizeRecipeInfoIcon = 16,
                    textRecipeInfo = recipe.creationDate.takeWhile { it != 'T' },
                    textFontSize = 10
                )
            }
        }
    }
}

fun getUnsafeOkHttpClient(): OkHttpClient {
    try {
        // Создаем доверенного менеджера, который доверяет всем сертификатам
        val trustAllCerts = arrayOf<TrustManager>(
            object : X509TrustManager {
                override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
                override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
                override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
            }
        )

        // Устанавливаем SSL-контекст с нашими доверенными сертификатами
        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCerts, java.security.SecureRandom())

        // Создаем OkHttpClient
        return OkHttpClient.Builder()
            .sslSocketFactory(sslContext.socketFactory, trustAllCerts[0] as X509TrustManager)
            .hostnameVerifier(HostnameVerifier { _, _ -> true })
            .build()
    } catch (e: Exception) {
        throw RuntimeException(e)
    }
}


fun createImageLoader(context: Context): ImageLoader {
    val okHttpClient = getUnsafeOkHttpClient()
    return ImageLoader.Builder(context)
        .okHttpClient { okHttpClient }
        .build()
}

@Composable
fun LoadImage(context: Context, imageUrl: String) {
    val imageLoader = createImageLoader(context)

    val painter = rememberAsyncImagePainter(
        model = imageUrl,
        imageLoader = imageLoader
    )

    Image(
        painter = painter,
        contentDescription = null,
        contentScale = ContentScale.Fit,
        modifier = Modifier
            .padding(start = 16.dp, top = 16.dp)
            .size(94.dp)
    )
}