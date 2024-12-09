package com.liontail.arfind

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.liontail.arfind.api.ApiService
import com.liontail.arfind.api.RetrofitClient
import com.liontail.arfind.notificaciones.NotificacionAdapter
import com.liontail.arfind.notificaciones.NotificacionDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificacionActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NotificacionAdapter
    private var notificaciones: MutableList<NotificacionDto> = mutableListOf()

    private val apiService: ApiService = RetrofitClient.getRetrofitInstance()
        .create(ApiService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notificacion)

        // Regresar a la MainActivity
        val linearVolver = findViewById<LinearLayout>(R.id.linear_volver)
        linearVolver.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        recyclerView = findViewById(R.id.listaNotifiaciones)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = NotificacionAdapter(notificaciones)
        recyclerView.adapter = adapter

        // Llamar al API para obtener las notificaciones
        cargarNotificaciones()
    }

    private fun cargarNotificaciones() {
        apiService.misNotificaciones()?.enqueue(object : Callback<List<NotificacionDto>?> {
            override fun onResponse(
                call: Call<List<NotificacionDto>?>,
                response: Response<List<NotificacionDto>?>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val nuevasNotificaciones = response.body() ?: emptyList()
                    // Actualizar la lista de notificaciones en el adapter
                    adapter.actualizarNotificaciones(nuevasNotificaciones)
                }
            }

            override fun onFailure(call: Call<List<NotificacionDto>?>, t: Throwable) {
                Log.e("NotificacionActivity", "Error en la llamada al API", t)
                Toast.makeText(
                    this@NotificacionActivity,
                    "No se pudieron cargar las notificaciones",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

}
