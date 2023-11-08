package com.mnj.dailyquotes.api

import com.mnj.dailyquotes.model.datamodel.Quote
import retrofit2.Response
import retrofit2.http.GET

interface QuotesApi {

    @GET("random")
    suspend fun getQuote(): Response<List<Quote>>

    @GET("today")
    suspend fun getQuoteOfTheDay():  Response<List<Quote>>
}