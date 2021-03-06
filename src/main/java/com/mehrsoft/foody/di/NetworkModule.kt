package com.mehrsoft.foody.di

import com.mehrsoft.foody.common.Constants.Companion.BASE_URL
import com.mehrsoft.foody.data.network.FoodRecipesApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

import javax.inject.Singleton


@Module()
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideHttpClient():OkHttpClient{
        return OkHttpClient.Builder()
                .readTimeout(15,TimeUnit.SECONDS)
                .connectTimeout(15,TimeUnit.SECONDS)
                .build()
    }


    @Singleton
    @Provides
    fun provideConverterFactory():GsonConverterFactory{
        return GsonConverterFactory.create()
    }
    @Singleton
    @Provides
    fun provideRetrofitInstance(httpClient: OkHttpClient,gsonConverterFactory: GsonConverterFactory):Retrofit{
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(httpClient)
                .addConverterFactory(gsonConverterFactory)
                .build()
    }

    @Singleton
    @Provides
    fun provideRetrofitApi(retrofit: Retrofit): FoodRecipesApi {
        return retrofit.create(FoodRecipesApi::class.java)
    }
}