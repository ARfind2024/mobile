package com.liontail.arfind.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.liontail.arfind.R
import com.liontail.arfind.api.ApiService
import com.liontail.arfind.api.RetrofitClient
import com.liontail.arfind.dispositivos.SubmitCodeDto
import com.liontail.arfind.dispositivos.SubmitCodeResponse
import com.liontail.arfind.firebase.presenter.SharedReferencesPresenter
import com.liontail.arfind.utils.EditTextHelperUtil
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ShareFragment : Fragment() {

    private val apiService: ApiService by lazy {
        RetrofitClient.getRetrofitInstance().create(ApiService::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_share, container, false)


        // Configurar botón para abrir diálogo
        view.findViewById<Button>(R.id.btn_agregar_compartido).setOnClickListener {
            showDialog()
        }

        return view
    }

    private fun showDialog() {
        val dialog = Dialog(requireActivity())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.bottomsheet_share_dispositivo)

        // Configurar los campos de texto y botón
        val editTexts = arrayOf(
            dialog.findViewById<EditText>(R.id.editText1),
            dialog.findViewById<EditText>(R.id.editText2),
            dialog.findViewById<EditText>(R.id.editText3),
            dialog.findViewById<EditText>(R.id.editText4),
            dialog.findViewById<EditText>(R.id.editText5),
            dialog.findViewById<EditText>(R.id.editText6)
        )
        EditTextHelperUtil.setupEditTexts(editTexts)

        val btnAgregarCupon = dialog.findViewById<Button>(R.id.btnAgregarCupon)
        btnAgregarCupon.setOnClickListener {
            handleAgregarCupon(editTexts, dialog, btnAgregarCupon)
        }

        // Configuración de la ventana del diálogo
        dialog.show()
        dialog.window?.apply {
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            attributes.windowAnimations = R.style.DialogAnimation
            setGravity(Gravity.BOTTOM)
        }
    }

    private fun handleAgregarCupon(
        editTexts: Array<EditText?>,
        dialog: Dialog,
        btnAgregarCupon: Button
    ) {
        btnAgregarCupon.isEnabled = false

        val valorCuponBuilder = StringBuilder()
        for (editText in editTexts) {
            val text = editText?.text.toString().trim()
            if (text.isEmpty()) {
                editText?.error = "Este campo no puede estar vacío"
                btnAgregarCupon.isEnabled = true
                return
            }
            valorCuponBuilder.append(text)
        }

        val codigoInvitado = valorCuponBuilder.toString()
        val submitCodeDto = SubmitCodeDto(codigoInvitado)
        Log.d("AGREGAR DISPOSITIVOS", "Payload enviado: $submitCodeDto")

        apiService.agregarDispositivo(submitCodeDto).enqueue(object : Callback<SubmitCodeResponse> {
            override fun onResponse(
                call: Call<SubmitCodeResponse>,
                response: Response<SubmitCodeResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    Log.i("AGREGAR DISPOSITIVOS", "body: ${response.body()}")
                } else {
                    Log.e("AGREGAR DISPOSITIVOS", "Error: ${response.errorBody()?.string()}")
                }
                SharedReferencesPresenter.cargarSharedReferences{
                    showBottomSheetResponse(response.isSuccessful)
                    dialog.dismiss()
                }

            }

            override fun onFailure(call: Call<SubmitCodeResponse>, t: Throwable) {
                Log.e("AGREGAR DISPOSITIVOS", "Error: ${t.message}")
                showBottomSheetResponse(false)
                btnAgregarCupon.isEnabled = true
            }
        })
    }


    private fun showBottomSheetResponse(success: Boolean) {
        val layout = if (success) R.layout.bottomsheet_response_ok else R.layout.bottomsheet_response_error
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog.setContentView(layout)
        bottomSheetDialog.show()
        Handler().postDelayed({ bottomSheetDialog.dismiss() }, 3000)
    }


    private fun cargarSharedPreferencesEnSegundoPlano(callback: () -> Unit) {
        lifecycleScope.launch {

            SharedReferencesPresenter.cargarSharedReferences { success ->
                if (success) {
                    callback()
                }
            }
        }
    }





}
