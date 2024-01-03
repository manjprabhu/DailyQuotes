package com.mnj.dailyquotes.di

import android.content.Context
import androidx.room.Room
import com.mnj.dailyquotes.api.QuotesApi
import com.mnj.dailyquotes.db.QuoteDao
import com.mnj.dailyquotes.db.QuoteDatabase
import com.mnj.dailyquotes.model.repository.QuotesRepository
import com.mnj.dailyquotes.model.repository.QuotesRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepoModule {
//Commented since we are using @Binds in RepositoryModule.kt to provide the binding
    /*@Provides
    fun provideRepository(apiService: QuotesApi, quoteDao:QuoteDao): QuotesRepository {
        return QuotesRepositoryImpl(apiService,quoteDao)
    }*/

//    @Singleton
//    @Provides
//    fun providesApi(retrofit: Retrofit): QuotesApi = retrofit.create(QuotesApi::class.java)

    // another syntax
    @Provides
    @Singleton
    fun providesQuotesApi(retrofit: Retrofit): QuotesApi {
        return retrofit.create(QuotesApi::class.java)
    }

    @Provides
    @Singleton
    fun providesQuoteDB(@ApplicationContext context: Context): QuoteDatabase {
        return Room.databaseBuilder(context, QuoteDatabase::class.java, "quotes_db.db").build()
    }

    @Provides
    @Singleton
    fun providesQuotesDao(database: QuoteDatabase): QuoteDao {
        return database.getDao()
    }
}