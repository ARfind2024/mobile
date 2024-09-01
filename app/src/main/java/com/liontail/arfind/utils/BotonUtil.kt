package com.liontail.arfind.utils

import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button

object BotonUtil {

    private const val tiempoDeEspera = 2000L // Tiempo de espera en milisegundos
    private const val tiempoDeEsperaX2 = 4000L
    fun deshabilitarPorTiempo(view: View) {
        view.isEnabled = false // Deshabilitar la vista
        Handler(Looper.getMainLooper()).postDelayed({
            view.isEnabled = true // Volver a habilitar la vista después del tiempo de espera
        }, tiempoDeEspera)
    }

    fun deshabilitarPorTiempoX2(view: View) {
        view.isEnabled = false // Deshabilitar la vista
        Handler(Looper.getMainLooper()).postDelayed({
            view.isEnabled = true // Volver a habilitar la vista después del tiempo de espera
        }, tiempoDeEsperaX2)
    }
}