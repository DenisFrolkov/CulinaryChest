package com.example.culinarychest.data.data.api

import android.annotation.SuppressLint
import com.example.culinarychest.data.data.repository.TokenManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

const val BASE_URL = "https://10.0.2.2:7286"

class RetrofitInstance(private var tokenManager: TokenManager) {

    val okHttpClient = OkHttpClient.Builder()
        .apply {
            try {
                val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                    @SuppressLint("TrustAllX509TrustManager")
                    override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {}

                    @SuppressLint("TrustAllX509TrustManager")
                    override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {}

                    override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
                })
                val sslContext = SSLContext.getInstance("SSL")
                sslContext.init(null, trustAllCerts, java.security.SecureRandom())
                sslSocketFactory(sslContext.socketFactory, trustAllCerts[0] as X509TrustManager)
                hostnameVerifier { _, _ -> true }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        .apply {
            addInterceptor { chain ->
                val original = chain.request()
                val requestBuilder = original.newBuilder()
                    .header("Authorization", "Bearer ${getToken()}")
                    .method(original.method, original.body)
                val request = requestBuilder.build()
                chain.proceed(request)
            }
        }
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    private fun getToken(): String? {
        return tokenManager.getToken()
    }

    val culinaryChestApi: CulinaryChestAPI = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()
        .create(CulinaryChestAPI::class.java)

}

