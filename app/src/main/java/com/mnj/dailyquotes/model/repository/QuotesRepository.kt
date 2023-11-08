package com.mnj.dailyquotes.model.repository

import com.mnj.dailyquotes.model.datamodel.Quote
import retrofit2.Response

interface QuotesRepository {

    suspend fun getRandomQuote(): Response<List<Quote>>

    suspend fun getQuoteOfTheDay():  Response<List<Quote>>
}