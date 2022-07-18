package com.example.notificationexample

import android.app.NotificationManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.RemoteInput
import androidx.core.content.getSystemService
import androidx.databinding.DataBindingUtil
import com.example.notificationexample.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySecondBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this@SecondActivity,
            R.layout.activity_second)
        receiveInput()
    }
    private fun receiveInput(){
        val intent=this.intent

        val remoteInput = RemoteInput.getResultsFromIntent(intent)
        if(remoteInput!=null){
            val inputString = remoteInput.getCharSequence("KEY_REPLY")
                .toString()
            binding.resultTextView.text=inputString
            val channelID="com.example.notificationexample.channel1"
            val notificationId=45

            val repliedNotification= NotificationCompat.Builder(this,channelID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentText("Your Reply Recieved")
                .build()

            val notificationManager:NotificationManager=
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(notificationId,repliedNotification)
        }
    }

}