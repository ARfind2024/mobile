package com.liontail.arfind.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText
import com.liontail.arfind.R

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Encontramos las vistas de la interfaz de usuario
        val nombreEditText = findViewById<TextInputEditText>(R.id.txt_register_nombre)
        val apellidoEditText = findViewById<TextInputEditText>(R.id.txt_register_apellido)
        val dniEditText = findViewById<TextInputEditText>(R.id.txt_register_dni)
        val correoEditText = findViewById<TextInputEditText>(R.id.txt_register_correo)
        val celularEditText = findViewById<TextInputEditText>(R.id.txt_register_celular)
        val passEditText = findViewById<TextInputEditText>(R.id.txt_register_pass)
        val confirmPassEditText = findViewById<TextInputEditText>(R.id.txt_register_confirmation_pass)
        val txtError = findViewById<TextView>(R.id.txt_error)
        val btnConfirmar = findViewById<Button>(R.id.btn_register_confirmar)


        val registerPresenter = RegisterPresenter(
            context = this,
            nombreEditText = nombreEditText,
            apellidoEditText = apellidoEditText,
            dniEditText = dniEditText,
            correoEditText = correoEditText,
            celularEditText = celularEditText,
            passEditText = passEditText,
            confirmPassEditText = confirmPassEditText,
            txtError = txtError,
            btnConfirmar = btnConfirmar
        )

        registerPresenter.setupConfirmarButton()
    }
}
