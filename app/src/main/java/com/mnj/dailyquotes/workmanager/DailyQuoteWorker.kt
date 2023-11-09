package com.mnj.dailyquotes.workmanager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltWorker
class DailyQuoteWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workParams: WorkerParameters,
    private val repo: QuotesRepository
) : CoroutineWorker(context, workParams) {

    override suspend fun doWork(): Result {
        println("==>> doWork() ......")
        CoroutineScope(Dispatchers.IO).launch {
            val result = repo.getQuoteOfTheDay()

            if (result.isSuccessful) {
                println("==>> doWork() isSuccessful......")
                val notificationIntent = Intent(applicationContext, QuotesActivity::class.java)
                    .setFlags(
                        Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                    )

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

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val notificationManager: NotificationManager =
                        applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

                    notificationManager.createNotificationChannel(
                        NotificationChannel(
                            applicationContext.getString(R.string.daily_quote_tag),
                            applicationContext.getString(R.string.daily_quote_tag),
                            NotificationManager.IMPORTANCE_DEFAULT
                        )
                    )
                }
                val managerCompat = NotificationManagerCompat.from(applicationContext)
                managerCompat.notify(Math.random().toInt(), builder.build())
            }
        }
        return Result.success()
    }
}