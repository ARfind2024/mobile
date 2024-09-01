package com.liontail.arfind

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class NoInternetActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_no_internet)

        val btnReintentar = findViewById<Button>(R.id.btnReintentar)

        btnReintentar.setOnClickListener {
            val intent = Intent(this, SplashScreenActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}