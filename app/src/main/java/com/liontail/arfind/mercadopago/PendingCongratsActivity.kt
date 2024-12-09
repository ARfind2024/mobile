package com.liontail.arfind.mercadopago

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.liontail.arfind.MainActivity
import com.liontail.arfind.R
import com.liontail.arfind.firebase.presenter.SharedReferencesPresenter
import com.liontail.arfind.utils.NetworkUtils
import kotlinx.coroutines.launch

class PendingCongratsActivity : AppCompatActivity() {

    lateinit var btn: Button
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pending_congrats)
        if (!NetworkUtils.isNetworkAvailable(this)) {
            NetworkUtils.redirectToNoInternetActivity(this)
        }

        btn = findViewById(R.id.btn_continuar)

        btn.setOnClickListener(View.OnClickListener { view ->
            cargarSharedPreferencesEnSegundoPlano {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        })

    }
    private fun cargarSharedPreferencesEnSegundoPlano(callback: () -> Unit) {
        lifecycleScope.launch {
            // Llamamos a cargarSharedReferences y pasamos el callback que se ejecutará al terminar.
            SharedReferencesPresenter.cargarSharedReferences { success ->
                if (success) {
                    // Si se cargaron correctamente las referencias, ejecutamos el callback
                    callback()
                } else {
                    // Si hubo un error al cargar las referencias, mostramos un mensaje
                    Toast.makeText(this@PendingCongratsActivity, "Error al cargar las referencias compartidas", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    @SuppressLint("MissingSuperCall")
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
    }
}