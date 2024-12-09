package com.liontail.arfind.usuario


import android.util.Log
import com.liontail.arfind.api.ApiService
import com.liontail.arfind.api.RetrofitClient
import com.liontail.arfind.firebase.dto.UsuarioDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.CompletableFuture

object UsuarioColeccion {

    // Obtiene la instancia de ApiService a través de RetrofitClient inicializado
    private val apiService: ApiService = RetrofitClient.getRetrofitInstance()
        .create(ApiService::class.java)

    // Método para obtener información del usuario de forma asíncrona
    fun obtenerUsuarioByIdAsync(): CompletableFuture<UsuarioDto?> {
        val future = CompletableFuture<UsuarioDto?>()

        val call: Call<UsuarioDto> = apiService.getUserById()

        Log.i("USUARIO", "Llamando al API para obtener la información del usuario.")

        call.enqueue(object : Callback<UsuarioDto> {
            override fun onResponse(
                call: Call<UsuarioDto>,
                response: Response<UsuarioDto>
            ) {
                if (response.isSuccessful) {
                    val usuario = response.body()
                    if (usuario != null) {
                        Log.i("USUARIO", "Respuesta exitosa con los datos del usuario.")
                        future.complete(usuario)  // Completa el future con la información del usuario
                    } else {
                        Log.i("USUARIO", "Respuesta exitosa pero sin datos de usuario, enviando null.")
                        future.complete(null)  // Enviar null si no hay datos de usuario
                    }
                } else {
                    // Si la respuesta no es exitosa, loguear el código y mensaje de error
                    Log.e("USUARIO", "Error en la respuesta: ${response.code()} - ${response.message()}")
                    future.complete(null)  // Completa con null en caso de error
                }
            }

            override fun onFailure(call: Call<UsuarioDto>, t: Throwable) {
                // Si ocurre un error al hacer la solicitud, logueamos el error y completamos con null
                Log.e("USUARIO", "Error al hacer la llamada: ${t.message}", t)
                future.complete(null)  // Completa con null en caso de fallo
            }
        })

        return future
    }
}
