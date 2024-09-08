package com.liontail.arfind.firebase.singleton

import com.liontail.arfind.firebase.coleccion.ProductoColeccion
import com.liontail.arfind.firebase.dto.ProductoDto
import kotlinx.coroutines.future.await

class ProductoListSingleton private constructor() {
    var lproductosDtos: List<ProductoDto>? = null

    companion object {
        private var instance: ProductoListSingleton? = null
        @Synchronized
        fun getInstance(): ProductoListSingleton? {
            if (instance == null) {
                instance = ProductoListSingleton()
            }
            return instance
        }

        fun destroyInstance() {
            instance = null
        }

        suspend fun cargarProductosAsync(): Boolean {
            return try {
                val productos = ProductoColeccion.obtenerProductosAsync().await()
                getInstance()?.lproductosDtos = productos
                true
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }

        fun obtenerProductos(): List<ProductoDto>? {
            return ProductoListSingleton.getInstance()?.lproductosDtos
        }



    }
}