package com.liontail.arfind

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.liontail.arfind.firebase.presenter.SharedReferencesPresenter

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SharedReferencesPresenter.cargarSharedReferences { success ->
            if (success) {
                val splashTimeOut = 2000L
                val handler = Handler(Looper.getMainLooper())
                handler.postDelayed({
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }, splashTimeOut)
            } else {
                Toast.makeText(this, "Error al cargar las referencias compartidas", Toast.LENGTH_SHORT).show()
            }
        }
    }
}