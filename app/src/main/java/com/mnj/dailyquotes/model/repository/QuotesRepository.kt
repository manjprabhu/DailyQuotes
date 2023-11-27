package com.mnj.dailyquotes.model.repository

import com.mnj.dailyquotes.db.QuoteEntity
import retrofit2.Response

interface QuotesRepository {

    suspend fun getRandomQuote(): Response<List<QuoteEntity>>

    suspend fun getQuoteOfTheDay(): Response<List<QuoteEntity>>

    suspend fun insert(quote: QuoteEntity): Long

    suspend fun deleteQuote(quote: QuoteEntity)

}