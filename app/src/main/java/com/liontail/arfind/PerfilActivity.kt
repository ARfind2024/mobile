package com.liontail.arfind

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.liontail.arfind.register.TerminoRegister

class PerfilActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        val ctnPerfilDetail = findViewById<LinearLayout>(R.id.ctnPerfilDetail)
        val ctnTc = findViewById<LinearLayout>(R.id.ctnTc)
        val ctnPreguntas = findViewById<LinearLayout>(R.id.ctnPreguntas)
        val txtCerrarSesion = findViewById<TextView>(R.id.txtCerrarSesion1)
        val backBtnProfile = findViewById<ImageView>(R.id.back_btn_profile)

        backBtnProfile.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        ctnPerfilDetail.setOnClickListener {
            val intent = Intent(this, PerfilDetailActivity::class.java)
            startActivity(intent)
            finish()
        }

        ctnTc.setOnClickListener {
            val url = "https://sites.google.com/view/tyc-arfind/inicio"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }

        ctnPreguntas.setOnClickListener {
            val url = "https://sites.google.com/view/arfind-faq/inicio"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }


        txtCerrarSesion.setOnClickListener {
            AlertDialog.Builder(this)
                .setMessage("¿Estás seguro de que deseas salir?")
                .setCancelable(false)
                .setPositiveButton("Sí") { _, _ ->
                    super.onBackPressedDispatcher.onBackPressed()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()

                }
                .setNegativeButton("No", null)
                .show()
        }

    }
    @SuppressLint("MissingSuperCall")
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {

    }
}