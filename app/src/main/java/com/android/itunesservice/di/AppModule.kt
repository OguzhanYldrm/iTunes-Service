package com.android.itunesservice.di

import com.android.itunesservice.api.ISearchService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    //Initiating base_url and getting country code from system.
    private val BASE_URL = "https://itunes.apple.com/"
    private val country = Locale.getDefault().country

    // This is a utility interceptor which logs every request with detail to the logcat.
    private val interceptor = run {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.apply {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }
    }

    //Okhttp object of the interceptor to add as client to retrofit object.
    private val okHttpClient = OkHttpClient.Builder()
        .addNetworkInterceptor(interceptor)
        .build()




    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        //.client(okHttpClient) //logs will be visible on logcat when this comment is opened.
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideSearchApi(retrofit: Retrofit) : ISearchService = retrofit.create(ISearchService::class.java)
}
