package com.liontail.arfind.firebase.singleton

import com.liontail.arfind.dispositivos.DispositivoDto
import kotlinx.coroutines.future.await

object DispositivoUnifiedListSingleton {
    private var unifiedList: List<DispositivoDto>? = null

    suspend fun cargarListasUnificadasAsync(): Boolean {
        return try {
            // Cargar dispositivos de usuarios
            val dispositivosUsuario = DispositivoListSingleton.cargarDispositivosAsync().let {
                DispositivoListSingleton.obtenerDispositivos() ?: emptyList()
            }

            // Cargar dispositivos de invitados
            val dispositivosInvitados = DispositivoInvitadosListSingleton.cargarDispositivosInvitadosAsync().let {
                DispositivoInvitadosListSingleton.obtenerDispositivos() ?: emptyList()
            }

            // Unificar ambas listas
            unifiedList = dispositivosUsuario + dispositivosInvitados
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun obtenerListaUnificada(): List<DispositivoDto>? {
        return unifiedList
    }

    fun destruirInstancia() {
        unifiedList = null
    }
}
