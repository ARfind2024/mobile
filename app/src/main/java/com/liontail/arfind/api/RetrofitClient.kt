package com.liontail.arfind.api

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private var retrofitInstance: Retrofit? = null

    // Recuperar SharedPreferences
    private var sharedPreferences: SharedPreferences? = null

    // Inicializar Retrofit
    fun initialize(context: Context) {
        if (retrofitInstance == null) {
            sharedPreferences = context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)

            // Crear un interceptor que agrega el token al encabezado Authorization
            val interceptor = Interceptor { chain ->
                val token = sharedPreferences?.getString("auth_token", null)
                if (token != null) {
                    Log.d(": : : TOKEN AUTENTICADO : : :", token)
                }
                // Si el token no es nulo, lo agregamos al encabezado Authorization
                val newRequest = if (token != null) {
                    chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer $token") // Token en el encabezado
                        .build()
                } else {
                    chain.request()
                }

                // Procedemos con la solicitud modificada
                chain.proceed(newRequest)
            }

            // Crear OkHttpClient con el interceptor
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()

            // Configurar Retrofit
            retrofitInstance = Retrofit.Builder()
                .baseUrl("https://arfindtiago-t22ijacwda-uc.a.run.app") // La URL base de tu API
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient) // Agregar el cliente con interceptor
                .build()
        }
    }

    // Obtener instancia de Retrofit
    fun getRetrofitInstance(): Retrofit {
        if (retrofitInstance == null) {
            throw IllegalStateException("RetrofitClient must be initialized with initialize(context) before use.")
        }
        return retrofitInstance!!
    }
}
