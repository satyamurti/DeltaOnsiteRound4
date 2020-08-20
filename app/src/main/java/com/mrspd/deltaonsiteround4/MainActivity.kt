package com.mrspd.deltaonsiteround4

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.TimePickerDialog.OnTimeSetListener
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log.d
import android.view.View
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() , OnTimeSetListener {
    private var mHour = 0
    private  var mMin:Int = 0
    private val REQUEST_CODE = 1

    private var mycalender: Calendar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createNotificationChannel()

        mycalender = Calendar.getInstance()

        if (intent.extras != null) {
            val ph1 = intent.extras!!.getString("ph")
            val msg1 = intent.extras!!.getString("msg")
            val h = intent.extras!!.getInt("h")
            val m = intent.extras!!.getInt("m")
            btPhoneNumber.setText(ph1)
            btMessage.setText(msg1)
        }
        if (!checkPermission(Manifest.permission.SEND_SMS)) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.SEND_SMS),
                REQUEST_CODE
            )
        }

    }
    fun checkPermission(perm: String?): Boolean {
        d("gghh","Permission check karra bhailog")
        val check = ContextCompat.checkSelfPermission(this, perm!!)
        return check == PackageManager.PERMISSION_GRANTED
    }

    override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
        d("gghh","Time Succesfully set bhailog")
        mHour = p1
        mMin = p2
    }
    companion object {
        const val CHANNEL_ID = "MYSMSCHANNEL"
    }
    fun openTImePicker(view: View) {
        val dialog: DialogFragment = com.mrspd.deltaonsiteround4.DialogFragment()
        dialog.show(supportFragmentManager, "time picker")
        d("gghh", "Timepicker open hua bhailog ")

    }
    private fun createNotificationChannel() {
        d("gghh","Noti Channel create karra bhailog")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "MY SMS Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(
                NotificationManager::class.java
            )
            manager.createNotificationChannel(serviceChannel)
        }
    }
    fun scheduleSMS(view: View) {
        val phone: String = btPhoneNumber.text.toString()
        val message: String = btMessage.text.toString()
        val intent = Intent(this, MessengingService::class.java)
        intent.putExtra("phonenumber", phone)
        intent.putExtra("message", message)
        intent.putExtra("hour", mHour)
        intent.putExtra("minute", mMin)
        startService(intent)
        d("gghh", "Service start hua bhailog ")
    }
}