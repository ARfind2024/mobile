package com.liontail.arfind.firebase.singleton

import com.liontail.arfind.firebase.coleccion.DispositivoColeccion
import com.liontail.arfind.firebase.coleccion.PlanColeccion
import com.liontail.arfind.firebase.dto.DispositivoDto
import com.liontail.arfind.firebase.dto.PlanDto
import kotlinx.coroutines.future.await

class PlanListSingleton private constructor() {
    var lplanDtos: List<PlanDto>? = null

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
                val planes = PlanColeccion.obtenerPlanesAsync().await()
                getInstance()?.lplanDtos = planes
                true
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }

        fun obtenerPlanes(): List<PlanDto>? {
            return PlanListSingleton.getInstance()?.lplanDtos
        }



    }
}