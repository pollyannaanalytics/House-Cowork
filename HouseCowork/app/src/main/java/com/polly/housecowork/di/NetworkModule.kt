package com.polly.housecowork.di

import android.content.Context
import android.util.Log
import com.polly.housecowork.network.AuthApiService
import com.polly.housecowork.network.ConnectionUtils
import com.polly.housecowork.network.HouseApiService
import com.polly.housecowork.network.ProfileApiService
import com.polly.housecowork.network.TaskApiService
import com.polly.housecowork.prefs.PrefsLicense
import com.polly.housecowork.utils.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(
        prefsLicense: PrefsLicense
    ): OkHttpClient {

        return OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(prefsLicense))
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    @Provides
    @Singleton
    fun provideAuthApiService(retrofit: Retrofit): AuthApiService {
        return retrofit.create(AuthApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideTaskService(retrofit: Retrofit): TaskApiService {
        return retrofit.create(TaskApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideProfileService(retrofit: Retrofit): ProfileApiService {
        return retrofit.create(ProfileApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideHouseService(retrofit: Retrofit): HouseApiService {
        return retrofit.create(HouseApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideConnectionUtils(@ApplicationContext context: Context): ConnectionUtils {
        return ConnectionUtils(context)
    }


}

@Singleton
class AuthInterceptor @Inject constructor(private val prefsLicense: PrefsLicense): Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        Log.d("AuthInterceptor", "token: ${prefsLicense.token}")
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer ${prefsLicense.token}")
            .build()

        Log.d("AuthInterceptor", """
            URL: ${request.url}
            Headers: ${request.headers}
            Method: ${request.method}
        """.trimIndent())

        return try {
            Log.d("AuthInterceptor", "Attempting to proceed with request...")
            val response = chain.proceed(request)
            Log.d("AuthInterceptor", """
                Response Code: ${response.code}
                Response Message: ${response.message}
                Response Headers: ${response.headers}
            """.trimIndent())
            response
        } catch (e: Exception) {
            Log.e("AuthInterceptor", "Error during request", e)
            throw e
        }
    }
}