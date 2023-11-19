package com.esa.foodrecipes.di

import com.esa.foodrecipes.data.Repository
import com.esa.foodrecipes.data.remote.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideRepository(apiService: ApiService): Repository = Repository(apiService)

}