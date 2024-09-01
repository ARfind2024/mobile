package com.liontail.arfind.firebase.presenter

import com.liontail.arfind.firebase.singleton.DispositivoListSingleton
import com.liontail.arfind.firebase.singleton.PlanListSingleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

object SharedReferencesPresenter {
    fun cargarSharedReferences(callback: (Boolean) -> Unit) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                // Ejecutar las operaciones asincrónicas en paralelo
                val cargarPlanesDeferred = async { PlanListSingleton.cargarPlanesAsync() }
                val cargarDispositivosDeferred = async { DispositivoListSingleton.cargarAdicionalesAsync() }


                // Esperar a que todas las operaciones se completen
                val resultadoPlanes = cargarPlanesDeferred.await()
                val resultadoDispositivos = cargarDispositivosDeferred.await()

                if (resultadoPlanes && resultadoDispositivos) {

                    callback(true)
                } else {
                    callback(false)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                callback(false)
            }
        }
    }
}