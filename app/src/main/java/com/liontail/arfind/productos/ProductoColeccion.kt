import com.liontail.arfind.api.ApiService
import com.liontail.arfind.api.RetrofitClient
import com.liontail.arfind.productos.ProductoDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.CompletableFuture

object ProductoColeccion {

    // Obtiene la instancia de ApiService a través de RetrofitClient inicializado
    private val apiService: ApiService = RetrofitClient.getRetrofitInstance()
        .create(ApiService::class.java)

    // Método para obtener productos de forma asíncrona
    fun obtenerProductosAsync(): CompletableFuture<List<ProductoDto>> {
        val future = CompletableFuture<List<ProductoDto>>()

        val call: Call<List<ProductoDto>> = apiService.getProductos()

        call.enqueue(object : Callback<List<ProductoDto>> {
            override fun onResponse(
                call: Call<List<ProductoDto>>,
                response: Response<List<ProductoDto>>,
            ) {
                if (response.isSuccessful) {
                    // Completa el futuro con la lista de productos obtenidos
                    future.complete(response.body())
                } else {
                    // Completa el futuro con una excepción si hubo error en la respuesta
                    future.completeExceptionally(Exception("Error en la respuesta de la API: ${response.code()}"))
                }
            }

            override fun onFailure(call: Call<List<ProductoDto>>, t: Throwable) {
                // Completa el futuro con una excepción en caso de fallo en la solicitud
                future.completeExceptionally(t)
            }
        })

        return future
    }

    // Método para actualizar el token cuando sea necesario
    // fun actualizarToken(nuevoToken: String) {
    //     RetrofitInstance.actualizarToken(nuevoToken)  // Actualiza el token en RetrofitInstance
    // }
}
