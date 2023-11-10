package com.mnj.dailyquotes.api

import okhttp3.Interceptor
import okhttp3.Response

class LoggerInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val response = chain.proceed(request)
        println(" ==>> Intercepted headers: ${request.headers}, Url:  ${request.url}")
        println("==>> Response : ${response.code}")

        when (response.code) {
            200 -> {
                println("==>> Success ...")
            }
            400 -> {
                println("==>> Bad Request...")
            }
            403 -> {
                println("==>> Forbidden...")
            }
        }
        return response
    }
}