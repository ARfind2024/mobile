package com.liontail.arfind.firebase.presenter

import android.util.Log
import com.liontail.arfind.firebase.singleton.DispositivoInvitadosListSingleton
import com.liontail.arfind.firebase.singleton.DispositivoListSingleton
import com.liontail.arfind.firebase.singleton.DispositivoUnifiedListSingleton
import com.liontail.arfind.firebase.singleton.PlanListSingleton
import com.liontail.arfind.firebase.singleton.ProductoListSingleton
import com.liontail.arfind.firebase.singleton.UsuarioSingleton
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
                val cargarDispositivosDeferred = async { DispositivoListSingleton.cargarDispositivosAsync() }
                val cargarUsuarioDeferred = async { UsuarioSingleton.cargarUsuarioAsync() }
                val cargarDispositivosInvitadoDeferred = async { DispositivoInvitadosListSingleton.cargarDispositivosInvitadosAsync() }
                val cargarPlanesDefferred = async { PlanListSingleton.cargarPlanesAsync() }


                // Esperar a que todas las operaciones se completen
                val resultadoProductos = cargarProductosDeferred.await()
                val resultadoDispositivos = cargarDispositivosDeferred.await()
                val resultadoUsuario = cargarUsuarioDeferred.await()
                val resultadoDispositivosInvitado = cargarDispositivosInvitadoDeferred.await()
                val resultadoPlanes =  cargarPlanesDefferred.await()

                // Cargar la lista unificada si las listas individuales se cargaron correctamente
                val resultadoListaUnificada = if (resultadoDispositivos && resultadoDispositivosInvitado) {
                    DispositivoUnifiedListSingleton.cargarListasUnificadasAsync()
                } else {
                    false
                }

                // Evaluar resultados
                if (resultadoProductos && resultadoDispositivos && resultadoUsuario && resultadoDispositivosInvitado && resultadoListaUnificada && resultadoPlanes) {
                    callback(true)
                } else {
                    Log.e("carga de shared", "Error en la carga:" +
                            " Productos: $resultadoProductos, " +
                            "Planes: $resultadoPlanes, " +
                            "Dispositivos: $resultadoDispositivos, " +
                            "Usuario: $resultadoUsuario, " +
                            "Dispositivos Invitado: $resultadoDispositivosInvitado, " +
                            "Lista Unificada: $resultadoListaUnificada")
                    callback(false)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                callback(false)
            }
        }
    }
}
