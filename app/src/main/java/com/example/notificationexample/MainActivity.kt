package com.example.notificationexample

import android.app.*
import android.content.Context
import androidx.core.app.RemoteInput
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.databinding.DataBindingUtil
import com.example.notificationexample.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val channelID="com.example.notificationexample.channel1"

    private var notificationManager:NotificationManager?=null

    private val KEY_REPLY="KEY_REPLY"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=DataBindingUtil.setContentView(this,R.layout.activity_main)

        notificationManager=getSystemService(Context.NOTIFICATION_SERVICE)
                as NotificationManager

        createNotificationChannel(channelID,"DemoNotification"
            ,"this is demo notification")

        binding.button.setOnClickListener {
            displayNotification()
        }

    }
    private fun displayNotification(){
        val notificationId=45

        val tapResultIntent=Intent(this@MainActivity,SecondActivity::class.java)
        val pendingIntent=PendingIntent
            .getActivity(this,0,tapResultIntent,PendingIntent.FLAG_UPDATE_CURRENT)


            //reply action
        val remoteInput:RemoteInput= RemoteInput.Builder(KEY_REPLY).run {
            setLabel("Insert Your Name Here")
            build()
        }

        val replyAction:NotificationCompat.Action=NotificationCompat.Action.Builder(
            0
        ,"REPLY"
        ,pendingIntent
                ).addRemoteInput(remoteInput).build()



        val Intent2=Intent(this@MainActivity,DetailActivity::class.java)
        val pendingIntent2=PendingIntent
            .getActivity(this,0,Intent2,PendingIntent.FLAG_UPDATE_CURRENT)


        //acton button 1
        val action:NotificationCompat.Action=
            NotificationCompat.Action.Builder(0,"Details",pendingIntent2).build()

        val Intent3=Intent(this@MainActivity,SettingsActivity::class.java)
        val pendingIntent3=PendingIntent
            .getActivity(this,0,Intent3,PendingIntent.FLAG_UPDATE_CURRENT)

        //action button 2
        val action2:NotificationCompat.Action=
            NotificationCompat.Action.Builder(0,"Settings",pendingIntent3).build()

        val notification=NotificationCompat.Builder(this@MainActivity,channelID)
            .setContentTitle("Demo Title")
            .setContentText("This is a demo notification")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setAutoCancel(true)
            //.setContentIntent(pendingIntent)
            .addAction(action)
            .addAction(action2)
            .addAction(replyAction)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()
        notificationManager?.notify(notificationId,notification)
    }
    private fun createNotificationChannel(id:String,name:String
                                          ,channelDescription : String )
    {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            val importance=NotificationManager.IMPORTANCE_HIGH
            val channel=NotificationChannel(id,name, importance).apply {
                description= channelDescription
            }
            notificationManager?.createNotificationChannel(channel)
        }
    }
}