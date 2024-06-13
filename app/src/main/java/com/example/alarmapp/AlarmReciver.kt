package com.example.alarmapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.os.Vibrator
import android.widget.Toast


class AlarmReciver : BroadcastReceiver() {


    companion object {
        var mediaPlayer: MediaPlayer? = null
    }
    override fun onReceive(context: Context?, intent: Intent?) {
        Toast.makeText(context, "Alarm Triggered!", Toast.LENGTH_SHORT).show()

        if (intent != null) {
            if (intent.action == "STOP_ALARM") {
                // Stop the alarm sound
                mediaPlayer?.stop()
                mediaPlayer?.reset()
                mediaPlayer?.release()
                mediaPlayer = null
            } else {
                Toast.makeText(context, "Alarm Triggered!", Toast.LENGTH_SHORT).show()

                mediaPlayer = MediaPlayer.create(context, R.raw.alarm_ton)
                mediaPlayer?.start()
            }
        }

    }
}