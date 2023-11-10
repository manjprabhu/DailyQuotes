package com.mnj.dailyquotes.api

import okhttp3.Interceptor
import okhttp3.Response

class SimpleLoggingInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val response = chain.proceed(request)
        println(" ==>> Intercepted headers: ${request.headers}, Url:  ${request.url}")
        println("==>> Response : ${response.code}")

        if (!response.isSuccessful) {
            when (response.code) {

                in 401 until 403 -> {
                    println("==>> Client exception...")
                }
                in 500 until 599 -> {
                    println("==>>Server exception...")
                }
            }
        } else {
            println("==>> Success ...")
        }
        return response
    }
}