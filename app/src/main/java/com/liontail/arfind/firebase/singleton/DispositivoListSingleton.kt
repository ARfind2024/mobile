package com.liontail.arfind.firebase.singleton

import com.liontail.arfind.dispositivos.DispositivoColeccion
import com.liontail.arfind.dispositivos.DispositivoDto
import kotlinx.coroutines.future.await

class DispositivoListSingleton private constructor() {
    var dispositivosDtos: List<DispositivoDto>? = null
    companion object {
        private var instance: DispositivoListSingleton? = null
        @Synchronized
        fun getInstance(): DispositivoListSingleton? {
            if (instance == null) {
                instance = DispositivoListSingleton()
            }
            return instance
        }
        fun destroyInstance() {
            instance = null
        }
        suspend fun cargarDispositivosAsync(): Boolean {
            return try {
                val dispositvos = DispositivoColeccion.obtenerDispositivosByUsuarioAsync().await()
                getInstance()?.dispositivosDtos = dispositvos
                true
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
        fun obtenerDispositivos(): List<DispositivoDto>? {
            return DispositivoListSingleton.getInstance()?.dispositivosDtos
        }
    }
}