package com.liontail.arfind

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.liontail.arfind.firebase.singleton.UsuarioSingleton

class PerfilDetailActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_detail)


        val txtNombre = findViewById<TextView>(R.id.txt_nombre)
        val txtEmail = findViewById<TextView>(R.id.txt_email_user_value)
        val txtTelefono = findViewById<TextView>(R.id.txt_telefono_user_value)


        val usuario = UsuarioSingleton.getInstance()?.usuarioDto

        if (usuario != null) {
            txtNombre.text = "${usuario.nombre} ${usuario.apellido}"
            txtEmail.text = usuario.correo
            txtTelefono.text = usuario.telefono
        }





        val backBtnProfile = findViewById<ImageView>(R.id.back_btn_profile)

        backBtnProfile.setOnClickListener {
            val intent = Intent(this, PerfilActivity::class.java)
            startActivity(intent)
            finish()
        }


    }


    @SuppressLint("MissingSuperCall")
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {

    }
}