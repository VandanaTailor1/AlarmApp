package com.example.alarmapp

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity
import com.example.alarmapp.databinding.ActivityMainBinding
import java.util.Calendar


class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var alarmManager: AlarmManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        alarmManager= getSystemService(ALARM_SERVICE)
                as AlarmManager

       // onToggleClicked(binding.toggleButton)
    }

    public fun onToggleClicked(view: View) {
      // val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val pendingIntent: PendingIntent

        if ((view as ToggleButton).isChecked) {
            Toast.makeText(this@MainActivity, "ALARM ON", Toast.LENGTH_SHORT).show()
            val calendar = Calendar.getInstance()

            calendar.set(Calendar.HOUR_OF_DAY, binding.timePicker.currentHour)
            calendar.set(Calendar.MINUTE, binding.timePicker.currentMinute)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)

            val intent = Intent(this, AlarmReciver::class.java)
            pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

            var time = calendar.timeInMillis
            if (System.currentTimeMillis() > time) {
                time += 1000 * 60 * 60 * 24
            }

            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, time, AlarmManager.INTERVAL_DAY, pendingIntent)

        } else {


            val intent = Intent(this, AlarmReciver::class.java)
            pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

            alarmManager.cancel(pendingIntent)

            // Send a broadcast to stop the alarm sound
            val stopIntent = Intent(this, AlarmReciver::class.java)
            stopIntent.action = "STOP_ALARM"
            sendBroadcast(stopIntent)

            Toast.makeText(this@MainActivity, "ALARM OFF", Toast.LENGTH_SHORT).show()

        }
    }

}