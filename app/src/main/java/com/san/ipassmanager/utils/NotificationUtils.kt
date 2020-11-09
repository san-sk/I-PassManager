package com.san.ipassmanager.utils

import android.app.NotificationManager
import android.content.Context


// NotificationData ID.
private const val NOTIFICATION_ID = 0
private const val REQUEST_CODE = 0
private const val FLAGS = 0

/**
 * Builds and delivers the notification.
 *
 * @param context, activity context.
 */
fun NotificationManager.sendNotification(
    nId: Int,
    cId: String,
    messageBody: String,
    context: Context
) {
/*    // Create the content intent for the notification, which launches
    // this activity
    val contentIntent = Intent(context, MainActivity::class.java)

    val contentPendingIntent = PendingIntent.getActivity(
        context,
        //NOTIFICATION_ID,
        nId,
        contentIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )

    val notifyImage = BitmapFactory.decodeResource(
        context.resources,
        R.drawable.ic_logo
    )
    val bigPicStyle = NotificationCompat.BigPictureStyle()
        .bigPicture(notifyImage)
        .bigLargeIcon(notifyImage)

    //snooze action
    val snoozeIntent = Intent(context, SnoozeReceiver::class.java)
    val snoozePendingIntent: PendingIntent = PendingIntent.getBroadcast(
        context,
        REQUEST_CODE,
        snoozeIntent,
        FLAGS
    )

    //cancel action
    val dismissIntent = Intent(context, DismissReceiver::class.java)
    val dismissPendingIntent: PendingIntent = PendingIntent.getBroadcast(
        context,
        REQUEST_CODE,
        dismissIntent,
        FLAGS
    )

    // Build the notification
    val builder = NotificationCompat.Builder(
        context,
        //applicationContext.getString(R.string.booking_notification_channel_id)
        cId
    )
        .setSmallIcon(R.drawable.ic_launcher_background)
        //.setContentTitle(applicationContext.getString(R.string.today_appointment))
        .setContentTitle(cId)
        .setContentText(messageBody)
        .setContentIntent(contentPendingIntent)
        .setAutoCancel(false)
        .setOngoing(true)
        .setColorized(true)
        .setStyle(bigPicStyle)
        .setLargeIcon(notifyImage)
        .addAction(
            R.drawable.ic_schedule,
            context.getString(R.string.snooze),
            snoozePendingIntent
        )
        .addAction(R.drawable.ic_error_white_24dp, "dismiss", dismissPendingIntent)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setCategory(NotificationCompat.CATEGORY_CALL)
    notify(NOTIFICATION_ID, builder.build())
}

*//**
 * Cancels all notifications.
 *
 *//*
fun NotificationManager.cancelNotifications() {
    cancelAll()
}


fun createChannel(channelId: String, channelName: String, context: Context?) {

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val notificationChannel = NotificationChannel(
            channelId,
            channelName,
            NotificationManager.IMPORTANCE_HIGH
        ).apply { setShowBadge(true) }

        notificationChannel.enableLights(true)
        notificationChannel.lightColor = Color.RED
        notificationChannel.enableVibration(true)
        notificationChannel.description = "HIMS"
        notificationChannel.lockscreenVisibility = NotificationManager.IMPORTANCE_HIGH

        val notificationManager = context?.getSystemService(
            NotificationManager::class.java
        )
        notificationManager?.createNotificationChannel(notificationChannel)
    }

}


fun deleteChannel(context: Context?, channelId: String?) {

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

        val notificationManager = context?.getSystemService(
            NotificationManager::class.java
        )
        if (channelId != null) {
            notificationManager?.deleteNotificationChannel(channelId)
        } else {
            notificationManager?.notificationChannels?.let {
                for (id in it) {
                    notificationManager.deleteNotificationChannel(id.id)
                }
            }

        }
    }
}

fun scheduleNotification(context: Context?, cId: String, delay: Long, data: Appointment?) {

    val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val notificationIntent = Intent(context, AlarmReceiver::class.java)
        .putExtra("DT", "${data?.appointmentDate}\n${data?.appointmentTime}")
        .putExtra("Type", data?.consultationType)
        .putExtra("ChannelId", cId)

    val pendingIntent = PendingIntent.getBroadcast(
        context,
        0,
        notificationIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            delay,
            pendingIntent
        )
    } else {
        alarmManager.set(AlarmManager.RTC_WAKEUP, delay, pendingIntent)
    }*/

    //alarmManager!![AlarmManager.RTC_WAKEUP, delay] = pendingIntent //reduce time
}


/*fun createNotification(context: Context, notificationData: NotificationData): Notification? {

    return when (notificationData.channelId) {

        "Appointment" -> {
            val contentIntent = Intent(context, MainActivity::class.java)
            val contentPendingIntent = PendingIntent.getActivity(
                context,
                notificationData.notificationId ?: 0,
                contentIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )

            val notifyImage = BitmapFactory.decodeResource(
                context.resources,
                notificationData.bigPicture!!.toInt()
            )
            val bigPicStyle = NotificationCompat.BigPictureStyle()
                .bigPicture(notifyImage)
                .bigLargeIcon(notifyImage)

            //snooze action
            val snoozeIntent = Intent(context, SnoozeReceiver::class.java)
            val snoozePendingIntent: PendingIntent = PendingIntent.getBroadcast(
                context,
                notificationData.requestCode ?: 0,
                snoozeIntent,
                notificationData.flags ?: 0
            )

            //cancel action
            val dismissIntent = Intent(context, DismissReceiver::class.java)
            val dismissPendingIntent: PendingIntent = PendingIntent.getBroadcast(
                context,
                REQUEST_CODE,
                dismissIntent,
                FLAGS
            )

            // Build the notification
             return  NotificationCompat.Builder(context, notificationData.channelId.toString())
                .setSmallIcon(R.drawable.ic_logo)
                .setContentTitle(notificationData.contentTitle)
                .setContentText(notificationData.message)
                .setContentIntent(contentPendingIntent)
                .setAutoCancel(false)
                .setOngoing(true)
                .setColorized(true)
                .setStyle(bigPicStyle)
                .setLargeIcon(notifyImage)
                .addAction(R.drawable.ic_schedule, context.getString(R.string.snooze), snoozePendingIntent)
                .addAction(R.drawable.ic_error_white_24dp, "dismiss", dismissPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_CALL)
                .build()
        }

        else -> null
    }

}*/

