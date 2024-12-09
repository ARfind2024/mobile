package com.liontail.arfind.firebase.singleton

import com.liontail.arfind.dispositivos.DispositivoColeccion
import com.liontail.arfind.dispositivos.DispositivoDto
import com.liontail.arfind.dispositivos.DispositivoInvitadoColeccion
import kotlinx.coroutines.future.await

class DispositivoInvitadosListSingleton private constructor() {
    var dispositivosDtos: List<DispositivoDto>? = null
    companion object {
        private var instance: DispositivoInvitadosListSingleton? = null
        @Synchronized
        fun getInstance(): DispositivoInvitadosListSingleton? {
            if (instance == null) {
                instance = DispositivoInvitadosListSingleton()
            }
            return instance
        }
        fun destroyInstance() {
            instance = null
        }
        suspend fun cargarDispositivosInvitadosAsync(): Boolean {
            return try {
                val dispositvos = DispositivoInvitadoColeccion.obtenerDispositivosInvitado().await()
                getInstance()?.dispositivosDtos = dispositvos
                true
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
        fun obtenerDispositivos(): List<DispositivoDto>? {
            return DispositivoInvitadosListSingleton.getInstance()?.dispositivosDtos
        }
    }
}