package com.liontail.arfind

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.liontail.arfind.firebase.presenter.SharedReferencesPresenter
import com.liontail.arfind.register.TerminoRegister
import com.liontail.arfind.utils.BotonUtil.deshabilitarPorTiempo
import com.liontail.arfind.utils.NetworkUtils
import com.liontail.arfind.utils.VibrateUtil
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var emailEditText: TextInputLayout
    private lateinit var passwordEditText: TextInputLayout
    private lateinit var sharedPreferences: SharedPreferences

    @SuppressLint("MissingSuperCall")
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        // Mostrar un cuadro de diálogo de confirmación antes de salir
        AlertDialog.Builder(this)
            .setMessage("¿Estás seguro de que deseas salir?")
            .setCancelable(false)
            .setPositiveButton("Sí") { _, _ -> super.onBackPressedDispatcher.onBackPressed() }
            .setNegativeButton("No", null)
            .show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (!NetworkUtils.isNetworkAvailable(this)) {
            NetworkUtils.redirectToNoInternetActivity(this)
        }

        auth = FirebaseAuth.getInstance()
        sharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE)

        val emailEditText = findViewById<EditText>(R.id.txt_input_correo)
        val passwordEditText = findViewById<EditText>(R.id.txt_input_pass)
        val btnLoginIngresar = findViewById<Button>(R.id.btn_login_ingresar)
        val txtRegistrar = findViewById<TextView>(R.id.txtRegistrar)

        btnLoginIngresar.setOnClickListener {
            VibrateUtil.vibrar(this,200)
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                txtRegistrar.isEnabled = false
                btnLoginIngresar.isEnabled = false
                loginUser(email, password, txtRegistrar, btnLoginIngresar)
            } else {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        // Redirigir a la actividad de registro si el usuario hace clic en "Registrar"
        txtRegistrar.setOnClickListener {
            VibrateUtil.vibrar(this,300)
            txtRegistrar.isEnabled = false
            btnLoginIngresar.isEnabled = false
            val intent = Intent(this, TerminoRegister::class.java)
            startActivity(intent)

            finish()

        }
    }

    private fun loginUser(email: String, password: String,txtRegistrar: TextView,btnLoginIngresar: Button) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->

                if (task.isSuccessful) {
                    // Obtener el token de autenticación
                    val user = auth.currentUser
                    user?.getIdToken(true)?.addOnCompleteListener { tokenTask ->

                        if (tokenTask.isSuccessful) {
                            val idToken = tokenTask.result?.token
                            if (idToken != null) {
                                saveTokenToPreferences(idToken)

                                Log.d(": : : TOKEN AUTENTICADO : : :", idToken)

                                cargarSharedPreferencesEnSegundoPlano {
                                    val intent = Intent(this, MainActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                }
                            }
                        } else {
                            Log.e(": : : TOKEN ERROR : : :", "Error retrieving token: ${tokenTask.exception?.message}")

                        }
                    }
                } else {
                    Log.e(": : : TOKEN ERROR : : :", "Error retrieving token")

                    txtRegistrar.isEnabled = true
                    btnLoginIngresar.isEnabled = true

                    Toast.makeText(this, "Error de autenticación. Verifica tus credenciales.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun saveTokenToPreferences(token: String) {
        val editor = sharedPreferences.edit()
        editor.putString("auth_token", token)
        editor.apply()
    }

    // Función que carga las referencias compartidas en segundo plano y llama al callback cuando haya terminado
    private fun cargarSharedPreferencesEnSegundoPlano(callback: () -> Unit) {
        lifecycleScope.launch {
            // Llamamos a cargarSharedReferences y pasamos el callback que se ejecutará al terminar.
            SharedReferencesPresenter.cargarSharedReferences { success ->
                if (success) {
                    // Si se cargaron correctamente las referencias, ejecutamos el callback
                    callback()
                } else {
                    // Si hubo un error al cargar las referencias, mostramos un mensaje
                    Toast.makeText(this@LoginActivity, "Error al cargar las referencias compartidas", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}
