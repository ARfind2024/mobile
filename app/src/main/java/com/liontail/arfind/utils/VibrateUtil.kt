package com.liontail.arfind.utils

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator

object VibrateUtil {
        fun vibrar(context: Context, duracion: Long) {
            val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // Para Android Oreo (API 26) y superiores
                vibrator.vibrate(VibrationEffect.createOneShot(duracion, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                // Para versiones anteriores a Oreo (API 26)
                vibrator.vibrate(duracion)
            }
        }
}
