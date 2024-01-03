package com.mnj.dailyquotes.di

import com.mnj.dailyquotes.model.repository.QuotesRepository
import com.mnj.dailyquotes.model.repository.QuotesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindRepository(quotesRepository: QuotesRepositoryImpl): QuotesRepository
}