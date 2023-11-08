package com.mnj.dailyquotes.model.repository

import com.mnj.dailyquotes.api.QuotesApi
import com.mnj.dailyquotes.model.datamodel.Quote
import retrofit2.Response
import javax.inject.Inject

class QuotesRepositoryImpl @Inject constructor(private val apiService: QuotesApi) :
    QuotesRepository {

    override suspend fun getRandomQuote(): Response<List<Quote>> {
        return apiService.getQuote()
    }

    override suspend fun getQuoteOfTheDay():  Response<List<Quote>> {
        return apiService.getQuoteOfTheDay()
    }
}