package com.example.accessibilityserviceapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var button: Button
//    private lateinit var tv1: TextView
//    private lateinit var tv2: TextView
//
//    private var applicationLabel: CharSequence? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button = findViewById(R.id.myBtn)
//        tv1 = findViewById(R.id.tv_appName)
//        tv2 = findViewById(R.id.tv_text)

        button.setOnClickListener {
            val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
            startActivity(intent)
        }
//        val sharedPreferences = applicationContext.getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE)
//        val text = sharedPreferences.getString(applicationLabel as String?, "")
//
//        tv1.text = applicationLabel
//        tv2.text = text
    }
}