package com.mehrsoft.foody.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mehrsoft.foody.MyApplication
import com.mehrsoft.foody.common.Constants.Companion.DATABASE_NAME
import com.mehrsoft.foody.data.local.RecipesDao
import com.mehrsoft.foody.data.local.RecipesDatabase

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn

import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDb(@ApplicationContext context: Context):RecipesDatabase{
        return Room.databaseBuilder(context,RecipesDatabase::class.java,DATABASE_NAME)
            .build()
    }


    @Singleton
    @Provides
    fun provideAuthTokenDao(db:RecipesDatabase):RecipesDao{
        return db.recipesDao()
    }





}




