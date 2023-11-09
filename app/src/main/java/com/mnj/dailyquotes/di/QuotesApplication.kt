package com.mnj.dailyquotes.di

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.*
import com.mnj.dailyquotes.R
import com.mnj.dailyquotes.workmanager.DailyQuoteWorker
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class QuotesApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder().setWorkerFactory(workerFactory)
            .setMinimumLoggingLevel(android.util.Log.DEBUG)
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        setUpWorker()
    }

    private fun setUpWorker() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .build()

        val workRequest = PeriodicWorkRequestBuilder<DailyQuoteWorker>(1, TimeUnit.MINUTES)
            .build()

        WorkManager.getInstance(this)
            .enqueue(
                workRequest
            )
    }
}