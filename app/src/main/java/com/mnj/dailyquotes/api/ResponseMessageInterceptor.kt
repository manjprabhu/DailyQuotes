package com.mnj.dailyquotes.api

import com.google.gson.Gson
import com.mnj.dailyquotes.model.datamodel.ResponseMessage
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Response
import okhttp3.ResponseBody

class ResponseMessageInterceptor : Interceptor {

    val mediaType = "application/json; charset=utf-8".toMediaType()

    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())

        if (response.isSuccessful) {
            val gson = Gson()
            val body = gson.toJson(
                ResponseMessage(response.code, "This is the response from Server....")
            )
            val responseBody: ResponseBody = ResponseBody.create(mediaType, body)

            val originalBody = response.body
            originalBody?.close()
            return response.newBuilder().body(responseBody).build();
        }
        return response
    }
}