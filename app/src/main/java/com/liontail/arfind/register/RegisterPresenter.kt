package com.liontail.arfind.register

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.liontail.arfind.api.ApiService
import com.liontail.arfind.api.RetrofitClient
import com.liontail.arfind.utils.ModalWifi
import com.liontail.arfind.utils.VibrateUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.properties.Delegates

class RegisterPresenter(

    private val context: Context,
    private val nombreEditText: TextInputEditText,
    private val apellidoEditText: TextInputEditText,
    private val dniEditText: TextInputEditText,
    private val correoEditText: TextInputEditText,
    private val celularEditText: TextInputEditText,
    private val passEditText: TextInputEditText,
    private val confirmPassEditText: TextInputEditText,
    private val txtError: TextView,
    private val btnConfirmar: Button


) {

    // Validación de cada campo
    private fun validateField(field: TextInputEditText, errorLayout: TextInputLayout, errorMessage: String): Boolean {
        return if (field.text.toString().trim().isEmpty()) {
            errorLayout.error = errorMessage
            false
        } else {
            errorLayout.error = null
            true
        }
    }


    private fun validatePasswords(): Boolean {
        val password = passEditText.text.toString().trim()
        val confirmPassword = confirmPassEditText.text.toString().trim()

        return if (password.isEmpty() || confirmPassword.isEmpty()) {
            txtError.text = "La contraseña y su confirmación no pueden estar vacías"
            false
        } else if (password != confirmPassword) {
            txtError.text = "Las contraseñas no coinciden"
            false
        } else {
            txtError.text = ""
            true
        }
    }

    private fun validateForm(): Boolean {
        val isNombreValid = validateField(nombreEditText, nombreEditText.parent.parent as TextInputLayout, "Ingrese su nombre")
        val isApellidoValid = validateField(apellidoEditText, apellidoEditText.parent.parent as TextInputLayout, "Ingrese su apellido")
        val isDniValid = validateField(dniEditText, dniEditText.parent.parent as TextInputLayout, "Ingrese su DNI")
        val isCorreoValid = validateField(correoEditText, correoEditText.parent.parent as TextInputLayout, "Ingrese su correo electrónico")
        val isCelularValid = validateField(celularEditText, celularEditText.parent.parent as TextInputLayout, "Ingrese su número de celular")
        val isPasswordsValid2 = validateField(passEditText,passEditText.parent.parent as TextInputLayout, "Ingrese su contraseña")
        val isPasswordsValid3 = validateField(confirmPassEditText,confirmPassEditText.parent.parent as TextInputLayout, "Ingrese su confirmacion de  contraseña")
        val isPasswordsValid = validatePasswords()

        return isNombreValid && isApellidoValid && isDniValid && isCorreoValid && isCelularValid && isPasswordsValid  && isPasswordsValid2 && isPasswordsValid3
    }

    // Configuración del botón "Confirmar"
    fun setupConfirmarButton() {
        btnConfirmar.setOnClickListener {
            if (ModalWifi.hayConexionInternet(context)) {
                if (validateForm()) {
                    VibrateUtil.vibrar(context,200)
                    btnConfirmar.isEnabled = false
                    // Crear instancia de ApiService
                    val apiService = RetrofitClient.getRetrofitInstance()
                        .create(ApiService::class.java)


                    // Crear objeto PinUsuario con el correo del usuario
                    val email = correoEditText.text?.toString() ?: ""
                    val nombre = nombreEditText.text?.toString() ?: ""
                    val pinUsuario = PinUsuario(email,nombre)

                    // Llamar al endpoint para enviar el código por correo
                    apiService.sendCodeByMail(pinUsuario).enqueue(object : Callback<MailResponse> {
                        override fun onResponse(call: Call<MailResponse>, response: Response<MailResponse>) {
                            if (response.isSuccessful) {
                                // Si el código se envió correctamente
                                val intent = Intent(context, ConfirmationCodeActivity::class.java)
                                intent.putExtra("EMAIL", correoEditText.text.toString())
                                intent.putExtra("PASSWORD", confirmPassEditText.text.toString())
                                intent.putExtra("NOMBRE", nombreEditText.text.toString())
                                intent.putExtra("APELLIDO", apellidoEditText.text.toString())
                                intent.putExtra("TELEFONO", celularEditText.text.toString())
                                intent.putExtra("EDAD",intent.getIntExtra("EDAD", 0))

                                context.startActivity(intent)
                            } else {
                                // Mostrar el error completo para depurar
                                val error = response.errorBody()?.string() ?: "Error desconocido"
                                Toast.makeText(context, "Error al enviar el código: $error", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<MailResponse>, t: Throwable) {
                            Log.e("NetworkError", "Error de red: ${t.message}")
                            Toast.makeText(context, "Error de red", Toast.LENGTH_SHORT).show()
                        }

                    })
                }
            } else {
                ModalWifi.mostrarModalWifi(context as AppCompatActivity)
            }
        }
    }
}
