package com.mnj.dailyquotes.api

import com.mnj.dailyquotes.db.QuoteEntity
import retrofit2.Response
import retrofit2.http.GET

interface QuotesApi {

    @GET("random")
    suspend fun getQuote(): Response<List<QuoteEntity>>

    @GET("today")
    suspend fun getQuoteOfTheDay():  Response<List<QuoteEntity>>
}