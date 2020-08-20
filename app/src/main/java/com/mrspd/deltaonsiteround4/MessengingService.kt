package com.mrspd.deltaonsiteround4

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import android.util.Log.d
import androidx.core.app.NotificationCompat
import com.mrspd.deltaonsiteround4.MainActivity.Companion.CHANNEL_ID
import java.util.*

class MessengingService : Service() {
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        var phoneNumber: String? = null
        var message: String? = null
        var hour = 0
        var min = 0
        d("gghh", "Inside Service bhailog ")

        if (intent.extras != null) {
            phoneNumber = intent.extras!!.getString("phonenumber")
            message = intent.extras!!.getString("message")
            min = intent.extras!!.getInt("minute")
            hour = intent.extras!!.getInt("hour")
        }
        val notifyIntent = Intent(this, MainActivity::class.java)
        notifyIntent.putExtra("message", message)
        notifyIntent.putExtra("phonenumber", phoneNumber)
        notifyIntent.putExtra("hour", hour)
        notifyIntent.putExtra("minute", min)
        val pendingIntent1 =
            PendingIntent.getActivity(this, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Notification ✨")
            .setContentText("Sending message bhailog 🚀")
            .setSmallIcon(R.drawable.bulb)
            .setContentIntent(pendingIntent1)
            .build()
        startForeground(1, notification)
        val c = Calendar.getInstance()
        c[Calendar.HOUR_OF_DAY] = hour
        c[Calendar.MINUTE] = min
        c[Calendar.SECOND] = 0
        val alarmManager =
            getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, BroadCastReceiver::class.java)
        intent.putExtra("phonenumber", phoneNumber)
        intent.putExtra("message", message)
        println(phoneNumber)
        println(message)
        val pendingIntent =
            PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.timeInMillis, pendingIntent)
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        d("gghh","Service Khallass")
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }
}
