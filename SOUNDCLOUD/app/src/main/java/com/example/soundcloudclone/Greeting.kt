package com.example.soundcloudclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class Greeting : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_greeting)
        val handler = Handler()
        handler.postDelayed(Runnable {
            finish()
        }, 3000)
    }
}