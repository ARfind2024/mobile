package com.liontail.arfind.firebase.singleton

import com.liontail.arfind.firebase.dto.UsuarioDto
import com.liontail.arfind.usuario.UsuarioColeccion

import kotlinx.coroutines.future.await

class UsuarioSingleton private constructor() {
    var usuarioDto: UsuarioDto? = null

    companion object {
        private var instance: UsuarioSingleton? = null

        @Synchronized
        fun getInstance(): UsuarioSingleton? {
            if (instance == null) {
                instance = UsuarioSingleton()
            }
            return instance
        }

        fun destroyInstance() {
            instance = null
        }

        suspend fun cargarUsuarioAsync(): Boolean {
            return try {
                // Cargar la información del usuario de forma asíncrona
                val usuario = UsuarioColeccion.obtenerUsuarioByIdAsync().await()
                getInstance()?.usuarioDto = usuario
                true
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }

        fun obtenerUsuario(): UsuarioDto? {
            return UsuarioSingleton.getInstance()?.usuarioDto
        }
    }
}
