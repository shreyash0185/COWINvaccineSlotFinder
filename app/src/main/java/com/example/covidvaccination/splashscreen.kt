package com.example.covidvaccination

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class splashscreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)
        supportActionBar!!.hide()

        val thread: Thread = object : Thread() {
            override fun run() {
                try {
                    sleep(4000)
                } catch (e: Exception) {
                } finally {
                    val intent = Intent(this@splashscreen, MainActivity::class.java)
                    startActivity(intent)
                }
            }
        }
        thread.start ();


    }
}