package com.mnj.dailyquotes.di

import com.mnj.dailyquotes.api.SimpleLoggingInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder().addInterceptor(SimpleLoggingInterceptor()).build()
    }

    @Singleton
    @Provides
    fun providesRetrofitBuilder(okHttpClient: OkHttpClient): Retrofit.Builder = Retrofit.Builder()
        .baseUrl("https://zenquotes.io/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)

    @Singleton
    @Provides
    fun providesRetrofit(retrofitBuilder: Retrofit.Builder): Retrofit {
        return retrofitBuilder.build()
    }

    //Dummy code to show Qualifier annotation usage
    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class ServerOne

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class ServerTwo

    @Provides
    @Singleton
    @ServerOne
    fun providesRetrofitOne(okHttpClient: OkHttpClient) = Retrofit.Builder()
        .baseUrl("www.example.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Singleton
    @Provides
    @ServerTwo
    fun providesRetrofitTwo(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl("www.someotherurl.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Singleton
    @Named("Server1")
    fun providesRetrofitThree(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl("www.someotherurl.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Singleton
    @Named("Server2")
    fun providesRetrofitFour(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl("www.someotherurl.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

}