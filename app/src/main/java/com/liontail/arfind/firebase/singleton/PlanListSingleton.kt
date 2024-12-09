package com.liontail.arfind.firebase.singleton

import com.liontail.arfind.planes.PlanesColeccion
import com.liontail.arfind.planes.PlanesDto
import com.liontail.arfind.productos.ProductoDto
import kotlinx.coroutines.future.await

class PlanListSingleton {
    var lPlanesDto: List<PlanesDto>? = null

    companion object {
        private var instance: PlanListSingleton? = null
        @Synchronized
        fun getInstance(): PlanListSingleton? {
            if (instance == null) {
                instance = PlanListSingleton()
            }
            return instance
        }

        fun destroyInstance() {
            instance = null
        }

        suspend fun cargarPlanesAsync(): Boolean {
            return try {
                val planes = PlanesColeccion.obtenerPlanesAsync().await()
                getInstance()?.lPlanesDto = planes
                true
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }

        fun obtenerPlanes(): List<PlanesDto>? {
            return PlanListSingleton.getInstance()?.lPlanesDto
        }

    }
}