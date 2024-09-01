package com.liontail.arfind

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.liontail.arfind.utils.NetworkUtils

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (!NetworkUtils.isNetworkAvailable(this)) {
            NetworkUtils.redirectToNoInternetActivity(this)
        }

        val btnLoginIngresar = findViewById<Button>(R.id.btn_login_ingresar)

        btnLoginIngresar.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}