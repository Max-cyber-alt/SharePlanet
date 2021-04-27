package com.mobiproplus.sharedplanet.di

import com.mobiproplus.sharedplanet.data.network.api.ServiceApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    @Provides
    @Singleton
    fun provideOkHttClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(object  : Interceptor {
                override fun intercept(chain: Interceptor.Chain): Response {
                    val original: Request = chain.request()
                    val originalHttpUrl: HttpUrl = original.url
                    val url = originalHttpUrl.newBuilder()
                        .addQueryParameter("api_key", ServiceApi.API_KEY)
                        .build()
                    val requestBuilder: Request.Builder = original.newBuilder()
                        .url(url)
                    val request: Request = requestBuilder.build()
                    return chain.proceed(request)
                }
            })
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(ServiceApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideServiceApi(retrofit: Retrofit): ServiceApi =
        retrofit.create(ServiceApi::class.java)

}