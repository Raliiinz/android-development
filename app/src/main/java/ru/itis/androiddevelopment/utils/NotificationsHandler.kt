package ru.itis.androiddevelopment.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import ru.itis.androiddevelopment.MainActivity
import ru.itis.androiddevelopment.R
import ru.itis.androiddevelopment.model.NotificationData
import ru.itis.androiddevelopment.model.NotificationType

class NotificationsHandler(
    private val appCtx: Context
) {
    private val notificationManager = appCtx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationsChannelsIfNeeded()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createNotificationsChannelsIfNeeded() {
        Constants.notificationsChannelsData.forEach { channelData ->
            if (notificationManager.getNotificationChannel(channelData.id) == null) {
                val channel = with(channelData) {
                    NotificationChannel(id, name, importance)
                }
                notificationManager.createNotificationChannel(channel)
            }
        }
    }

    fun showNotification(data: NotificationData) {
        val index = when (data.notificationType) {
            NotificationType.LOW-> {
                0
            }
            NotificationType.PRIVATE -> {
                2
            }
            NotificationType.URGENT -> {
                3
            }
            else -> 1
        }

        val channelId = Constants.notificationsChannelsData[index].id

        val activityIntent = Intent(appCtx, MainActivity :: class.java).apply {
            putExtra("isFromNotification", true)
        }

        val pendingIntent = PendingIntent.getActivity(
            appCtx,
            0,
            activityIntent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )

        val notificationBuilder = NotificationCompat.Builder(appCtx, channelId)
            .setSmallIcon(R.drawable.ic_heart_broken_24)
            .setContentTitle(data.title)
            .setChannelId(channelId)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        data.message.let { message ->
            notificationBuilder.setContentText(message)
        }

        notificationManager.notify(data.id, notificationBuilder.build())
    }
}