package com.liontail.arfind.register

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.liontail.arfind.LoginActivity
import com.liontail.arfind.R
class TerminoRegister : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_termino_register)



        // Referencia de los elementos de la UI
        val switchTerminos: Switch = findViewById(R.id.swTerminos)
        val editEdad: EditText = findViewById(R.id.editEdad)
        val txtError: TextView = findViewById(R.id.txt_error_tc)
        val btnContinuar: Button = findViewById(R.id.continuar)

        val linearLayout = findViewById<LinearLayout>(R.id.ctnBackProfile)

        linearLayout.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.putExtra("EDAD",editEdad.text)
            startActivity(intent)
            finish()
        }

        // Configuración del botón "Continuar"
        RegisterTerminosPresenter.setupContinuarButton(
            btnContinuar,
            switchTerminos,
            txtError,
            editEdad,
            this
        )
    }
    @SuppressLint("MissingSuperCall")
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
    }
}
