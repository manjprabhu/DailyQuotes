package com.mnj.dailyquotes.di

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class QuotesApplication: Application() {

    override fun onCreate() {
        super.onCreate()
    }
}