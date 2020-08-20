package com.mrspd.deltaonsiteround4

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.telephony.SmsManager
import android.util.Log
import android.util.Log.d
import android.widget.Toast
import androidx.core.content.ContextCompat

class BroadCastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        var phoneNumber: String? = null
        var message: String? = null
        Log.d(TAG, "onReceive")
        if (intent.extras != null) {
            phoneNumber = intent.extras!!.getString("phonenumber")
            message = intent.extras!!.getString("message")
        }
        if (phoneNumber == null || phoneNumber.isEmpty() || message == null || message.isEmpty()) {
            return
        }
        if (checkPermission(Manifest.permission.SEND_SMS, context)) {
            val smsManager = SmsManager.getDefault()
            smsManager.sendTextMessage(phoneNumber, null, message, null, null)
            Toast.makeText(context, "Message send hua bhailog", Toast.LENGTH_SHORT).show()
            d("gghh","Message send hua bhailog")
        } else {
            Toast.makeText(context, "Message send nahi hua bhailog", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkPermission(perm: String?, context: Context?): Boolean {
        val check = ContextCompat.checkSelfPermission(context!!, perm!!)
        return check == PackageManager.PERMISSION_GRANTED
    }

    companion object {
        private const val TAG = "yes"
    }
}