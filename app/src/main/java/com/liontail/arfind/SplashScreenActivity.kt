package com.liontail.arfind

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.liontail.arfind.api.RetrofitClient
import com.liontail.arfind.firebase.presenter.SharedReferencesPresenter

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializar Retrofit
        RetrofitClient.initialize(this)

        // Establecer el layout del Splash Screen
        setContentView(R.layout.activity_splash_screen)

        // Mostrar el splash screen durante un tiempo fijo (2000 ms)
        val splashTimeOut = 2000L
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            // Iniciar LoginActivity después de mostrar el splash
            startActivity(Intent(this, LoginActivity::class.java))
            finish()  // Cerrar la actividad de Splash
        }, splashTimeOut)

        // Cargar SharedPreferences en segundo plano sin bloquear la interfaz de usuario
        // cargarSharedPreferencesEnSegundoPlano()
    }
}