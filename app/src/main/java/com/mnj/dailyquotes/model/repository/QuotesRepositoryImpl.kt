package com.mnj.dailyquotes.model.repository

import com.mnj.dailyquotes.api.QuotesApi
import com.mnj.dailyquotes.db.QuoteDao
import com.mnj.dailyquotes.db.QuoteEntity
import retrofit2.Response
import javax.inject.Inject

class QuotesRepositoryImpl @Inject constructor(
    private val apiService: QuotesApi,
    private val quoteDao: QuoteDao
) :
    QuotesRepository {

    override suspend fun getRandomQuote(): Response<List<QuoteEntity>> {
        return apiService.getQuote()
    }

    override suspend fun getQuoteOfTheDay(): Response<List<QuoteEntity>> {
        return apiService.getQuoteOfTheDay()
    }

    override suspend fun insert(quote: QuoteEntity): Long {
        return quoteDao.insertQuote(quote)
    }

    override suspend fun deleteQuote(quote: QuoteEntity) {
        quoteDao.deleteSavedQuote(quote)
    }
}