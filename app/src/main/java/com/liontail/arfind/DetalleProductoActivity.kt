package com.liontail.arfind

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

class DetalleProductoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_producto)

        val  btnContinuar = findViewById<Button>(R.id.btn_continuar)
        val ctnBackProfile = findViewById<ImageView>(R.id.back_btn_profile)

        btnContinuar.setOnClickListener{
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
            finish()
        }
        ctnBackProfile.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }


    }
}