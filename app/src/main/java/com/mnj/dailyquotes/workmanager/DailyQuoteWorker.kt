package com.mnj.dailyquotes.workmanager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.mnj.dailyquotes.R
import com.mnj.dailyquotes.model.repository.QuotesRepository
import com.mnj.dailyquotes.ui.view.QuotesActivity
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.coroutineScope


@HiltWorker
class DailyQuoteWorker @AssistedInject constructor(
    @Assisted val context: Context,
    @Assisted workParams: WorkerParameters,
    private val repo: QuotesRepository
) : CoroutineWorker(context, workParams) {

    override suspend fun doWork(): Result = coroutineScope {
        println("==>> doWork() ......")
        val result = repo.getQuoteOfTheDay()
            println("==>> doWork() isSuccessful......")

            if (result.isSuccessful) {

                val notificationIntent =
                    Intent(applicationContext, QuotesActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                    }

                val pendingIntent: PendingIntent = PendingIntent.getActivity(
                    applicationContext,
                    0,
                    notificationIntent,
                    PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
                )

                val builder = NotificationCompat.Builder(
                    applicationContext,
                    applicationContext.getString(R.string.daily_quote_tag)
                )
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle(applicationContext.getString(R.string.notification_title))
                    .setContentText(
                        result.body()!![0].quote
                    )
                    .setStyle(
                        NotificationCompat.BigTextStyle()
                            .bigText(

                                result.body()!![0].quote + "\n\n- ${result.body()!![0].author}"
                            )
                    )
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setContentIntent(pendingIntent)
                    .setSound(
                        RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                    )
                    .setAutoCancel(true)

                val notificationManager: NotificationManager =
                    applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

                notificationManager.createNotificationChannel(
                    NotificationChannel(
                        applicationContext.getString(R.string.daily_quote_tag),
                        applicationContext.getString(R.string.daily_quote_tag),
                        NotificationManager.IMPORTANCE_DEFAULT
                    )
                )
                val managerCompat = NotificationManagerCompat.from(applicationContext)
                managerCompat.notify(Math.random().toInt(), builder.build())

        }
        Result.success()
    }
}