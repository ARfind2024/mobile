package com.liontail.arfind.dispositivos

import android.util.Log
import com.liontail.arfind.api.ApiService
import com.liontail.arfind.api.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.CompletableFuture
object DispositivoInvitadoColeccion {

        // Obtiene la instancia de ApiService a través de RetrofitClient inicializado
        private val apiService: ApiService = RetrofitClient.getRetrofitInstance()
            .create(ApiService::class.java)

        // Método para obtener dispositivos de forma asíncrona
        fun obtenerDispositivosInvitado(): CompletableFuture<List<DispositivoDto>> {
            val future = CompletableFuture<List<DispositivoDto>>()

            val call: Call<List<DispositivoDto>> = apiService.getDispositivosInvitados()

            Log.i("DISPOSITIVOS", "Llamando al API para obtener los dispositivos.")

            call.enqueue(object : Callback<List<DispositivoDto>> {
                override fun onResponse(
                    call: Call<List<DispositivoDto>>,
                    response: Response<List<DispositivoDto>>
                ) {
                    if (response.isSuccessful) {
                        val dispositivos = response.body()
                        if (dispositivos != null && dispositivos.isNotEmpty()) {
                            Log.i("DISPOSITIVOS", "Respuesta exitosa con dispositivos: ${dispositivos.size} dispositivos.")
                            future.complete(dispositivos)  // Completa el future con la lista de dispositivos
                        } else {
                            Log.i("DISPOSITIVOS", "Respuesta exitosa pero sin dispositivos, enviando lista vacía.")
                            future.complete(emptyList())  // Enviar lista vacía si la respuesta está vacía
                        }
                    } else {
                        // Si la respuesta no es exitosa, loguear el código y mensaje de error
                        Log.e("DISPOSITIVOS", "Error en la respuesta: ${response.code()} - ${response.message()}")
                        future.complete(emptyList())  // Completa con lista vacía en caso de error
                    }
                }

                override fun onFailure(call: Call<List<DispositivoDto>>, t: Throwable) {
                    // Si ocurre un error al hacer la solicitud, logueamos el error y completamos con una lista vacía
                    Log.e("DISPOSITIVOS", "Error al hacer la llamada: ${t.message}", t)
                    future.complete(emptyList())  // Completa con lista vacía en caso de fallo
                }
            })

            return future
        }
    }

