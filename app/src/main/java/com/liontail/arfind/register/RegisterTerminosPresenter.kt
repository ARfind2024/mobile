package com.liontail.arfind.register

import android.content.Intent
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.liontail.arfind.utils.ModalWifi
import com.liontail.arfind.utils.VibrateUtil

object RegisterTerminosPresenter {

    // Validación del switch para los términos y condiciones
    private fun validateSwitch(aSwitch: Switch, txtError: TextView): Boolean {
        return if (aSwitch.isChecked) {
            true
        } else {
            txtError.text = "Debes aceptar las condiciones"
            false
        }
    }

    // Validación de la edad
    private fun validateAge(editText: EditText, txtError: TextView): Boolean {
        val input = editText.text.toString().trim()
        return if (input.isNotEmpty()) {
            try {
                val age = input.toInt()
                if (age >= 18) {
                    true
                } else {
                    txtError.text = "Debes ser mayor a 18"
                    false
                }
            } catch (e: NumberFormatException) {
                txtError.text = "Debes ingresar una edad válida"
                false
            }
        } else {
            txtError.text = "Debes ingresar tu edad"
            false
        }
    }

    // Validación completa de la actividad (edad y términos)
    fun validateActivityRegisterTerminosAge(aSwitch: Switch, txtError: TextView, editText: EditText): Boolean {
        val isAgeValid = validateAge(editText, txtError)
        val isSwitchValid = validateSwitch(aSwitch, txtError)
        return isAgeValid && isSwitchValid
    }

    // Configuración del botón "Continuar"
    fun setupContinuarButton(
        btnContinuar: Button,
        aSwitch: Switch,
        txtError: TextView,
        editText: EditText,
        activity: AppCompatActivity
    ) {
        btnContinuar.setOnClickListener {
            if (ModalWifi.hayConexionInternet(activity)) {
                if (validateActivityRegisterTerminosAge(aSwitch, txtError, editText)) {

                    VibrateUtil.vibrar(activity,200)
                    btnContinuar.isEnabled = false
                    txtError.text = ""

                    val edadString = editText.text.toString().trim()
                    val edad = edadString.toInt()

                    // Navega a la actividad de registro con la edad como extra
                    val intent = Intent(activity, RegisterActivity::class.java)
                    intent.putExtra("EDAD_EXTRA", edad)
                    activity.startActivity(intent)
                }
            } else {
                ModalWifi.mostrarModalWifi(activity)
            }
        }
    }
}
