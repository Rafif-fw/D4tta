package com.example.datasetmanagement.network

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "com.example.datasetmanagement.NOTIFY") {
            val title = intent.getStringExtra("title") ?: "No title"
            val message = intent.getStringExtra("message") ?: "No message"
            val notificationService = NotificationService(context)
            notificationService.showNotification(title, message)
        }
    }
}
