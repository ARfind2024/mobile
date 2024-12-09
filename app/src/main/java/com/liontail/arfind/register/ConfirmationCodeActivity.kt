package com.liontail.arfind.register

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.liontail.arfind.LoginActivity
import com.liontail.arfind.R
import com.liontail.arfind.api.ApiService
import com.liontail.arfind.api.RetrofitClient
import com.liontail.arfind.utils.VibrateUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.properties.Delegates

class ConfirmationCodeActivity : AppCompatActivity() {

    private lateinit var email: String
    private lateinit var password: String
    private lateinit var nombre: String
    private lateinit var apellido: String
    private lateinit var telefono: String
    private var edad by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmation_code)

        val editText1 = findViewById<EditText>(R.id.editText1)
        val editText2 = findViewById<EditText>(R.id.editText2)
        val editText3 = findViewById<EditText>(R.id.editText3)
        val editText4 = findViewById<EditText>(R.id.editText4)
        val editText5 = findViewById<EditText>(R.id.editText5)
        val editText6 = findViewById<EditText>(R.id.editText6)

        val btnContinuarCode = findViewById<Button>(R.id.btnVerificarCodePass)
        this.email = intent.getStringExtra("EMAIL") ?: ""
        this.password = intent.getStringExtra("PASSWORD") ?: ""
        this.nombre = intent.getStringExtra("NOMBRE") ?: ""
        this.apellido = intent.getStringExtra("APELLIDO") ?: ""
        this.telefono = intent.getStringExtra("TELEFONO") ?: ""
        this.edad = intent.getIntExtra("EDAD", 0)

        btnContinuarCode.setOnClickListener {
            VibrateUtil.vibrar(this,100)
            btnContinuarCode.isEnabled = false


            val email = this.email
            val pin = editText1.text.toString() +
                    editText2.text.toString() +
                    editText3.text.toString() +
                    editText4.text.toString() +
                    editText5.text.toString() +
                    editText6.text.toString()

            println(email)
            println("pin: $pin")
            println(nombre)
            println(apellido)
            println(telefono)
            println(edad)

            // Verificar que el email y el pin no estén vacíos
            if (pin.isNotEmpty()) {
                verifyPin(email, pin)
            } else {
                Toast.makeText(this, "Por favor, ingresa tanto el correo como el PIN", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun verifyPin(email: String, pin: String) {
        // Crear una instancia de Retrofit y ApiService
        val apiService = RetrofitClient.getRetrofitInstance()
            .create(ApiService::class.java)

        // Llamar al método de la API para verificar el PIN
        apiService.isPinOk(email, pin).enqueue(object : Callback<PinVerify> {
            override fun onResponse(call: Call<PinVerify>, response: Response<PinVerify>) {
                if (response.isSuccessful) {
                    val pinVerify = response.body()
                    if (pinVerify != null && pinVerify.result) {

                        // Crear el objeto UserInfo
                        val userInfo = UserInfo(
                            email = email,
                            password = password,
                            nombre = nombre,
                            apellido = apellido,
                            telefono = telefono,
                            edad = edad
                        )

                        // Llamar a la API para registrar al usuario
                        registerUser(userInfo)

                    } else {
                        // El PIN es inválido
                        Toast.makeText(this@ConfirmationCodeActivity, "PIN inválido", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // Manejar el error
                    Toast.makeText(this@ConfirmationCodeActivity, "Error al verificar el PIN", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<PinVerify>, t: Throwable) {
                // Manejar la falla (por ejemplo, error de red)
                Toast.makeText(this@ConfirmationCodeActivity, "Error de red", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun registerUser(userInfo: UserInfo) {
        val apiService = RetrofitClient.getRetrofitInstance()
            .create(ApiService::class.java)

        // Llamar al método de la API para registrar al usuario
        apiService.registerUser(userInfo).enqueue(object : Callback<ResponseUserInfo> {
            override fun onResponse(call: Call<ResponseUserInfo>, response: Response<ResponseUserInfo>) {
                if (response.isSuccessful) {
                    val responseUserInfo = response.body()
                    if (responseUserInfo != null) {
                        // Registro exitoso, pasar a la siguiente actividad
                        val intent = Intent(this@ConfirmationCodeActivity, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        // Manejar el caso en que el registro no fue exitoso
                        Toast.makeText(this@ConfirmationCodeActivity, "Error al registrar el usuario", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // Manejar el error
                    Toast.makeText(this@ConfirmationCodeActivity, "Error en la respuesta al registrar el usuario", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseUserInfo>, t: Throwable) {
                // Manejar la falla (por ejemplo, error de red)
                Toast.makeText(this@ConfirmationCodeActivity, "Error de red", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
