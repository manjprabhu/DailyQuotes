package com.mnj.dailyquotes.di

import com.mnj.dailyquotes.api.QuotesApi
import com.mnj.dailyquotes.model.repository.QuotesRepository
import com.mnj.dailyquotes.model.repository.QuotesRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class RepoModule {

    @Provides
    fun provideRepository(apiService: QuotesApi): QuotesRepository {
        return QuotesRepositoryImpl(apiService)
    }

    @Provides
    fun providesApi(retrofit: Retrofit): QuotesApi = retrofit.create(QuotesApi::class.java)
}