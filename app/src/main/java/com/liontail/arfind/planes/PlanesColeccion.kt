package com.liontail.arfind.planes

import com.liontail.arfind.api.ApiService
import com.liontail.arfind.api.RetrofitClient
import com.liontail.arfind.productos.ProductoDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.CompletableFuture

object PlanesColeccion {

    // Obtiene la instancia de ApiService a través de RetrofitClient inicializado
    private val apiService: ApiService = RetrofitClient.getRetrofitInstance()
        .create(ApiService::class.java)

    // Método para obtener productos de forma asíncrona
    fun obtenerPlanesAsync(): CompletableFuture<List<PlanesDto>> {
        val future = CompletableFuture<List<PlanesDto>>()

        val call: Call<List<PlanesDto>> = apiService.getPlanes()

        call.enqueue(object : Callback<List<PlanesDto>> {
            override fun onResponse(
                call: Call<List<PlanesDto>>,
                response: Response<List<PlanesDto>>,
            ) {
                if (response.isSuccessful) {
                    // Completa el futuro con la lista de productos obtenidos
                    future.complete(response.body())
                } else {
                    // Completa el futuro con una excepción si hubo error en la respuesta
                    future.completeExceptionally(Exception("Error en la respuesta de la API: ${response.code()}"))
                }
            }

            override fun onFailure(call: Call<List<PlanesDto>>, t: Throwable) {
                // Completa el futuro con una excepción en caso de fallo en la solicitud
                future.completeExceptionally(t)
            }
        })

        return future
    }
}