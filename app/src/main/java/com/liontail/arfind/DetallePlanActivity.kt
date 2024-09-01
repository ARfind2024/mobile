package com.liontail.arfind

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class DetallePlanActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_plan)

        val  btnContinuar = findViewById<Button>(R.id.btn_continuar)

        btnContinuar.setOnClickListener{
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
            finish()
        }


    }
}