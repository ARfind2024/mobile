package com.liontail.arfind.firebase.presenter

import android.util.Log
import com.liontail.arfind.firebase.singleton.DispositivoListSingleton
import com.liontail.arfind.firebase.singleton.ProductoListSingleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

object SharedReferencesPresenter {
    fun cargarSharedReferences(callback: (Boolean) -> Unit) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                // Ejecutar las operaciones asincrónicas en paralelo
                val cargarProductosDeferred = async { ProductoListSingleton.cargarProductosAsync() }
                val cargarDispositivosDeferred = async { DispositivoListSingleton.cargarAdicionalesAsync() }


                // Esperar a que todas las operaciones se completen
                val resultadoProductos = cargarProductosDeferred.await()
                val resultadoDispositivos = cargarDispositivosDeferred.await()

                if (resultadoProductos && resultadoDispositivos) {
                    callback(true)
                } else {
                    Log.e("carga de shared", "Error en la carga: Planes: $resultadoProductos, Dispositivos: $resultadoDispositivos")
                    callback(false)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                callback(false)
            }
        }
    }
}