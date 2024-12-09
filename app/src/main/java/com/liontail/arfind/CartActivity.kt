package com.liontail.arfind

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.liontail.arfind.api.ApiService
import com.liontail.arfind.api.RetrofitClient

//import com.liontail.arfind.api.RetrofitClient
import com.liontail.arfind.productos.ProductoDto
import com.liontail.arfind.firebase.singleton.ProductoListSingleton
import com.liontail.arfind.mercadopago.MPCheckout
import com.liontail.arfind.utils.VibrateUtil


class CartActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        val apiService: ApiService = RetrofitClient.getRetrofitInstance()
            .create(ApiService::class.java)
        val mp = MPCheckout(this@CartActivity,apiService)
        var producto: ProductoDto? = null
        var planId : String? = ""

        //val apiService: ApiService = RetrofitClient.retrofitInstance.create(ApiService::class.java)

        val productoPosition = intent.getIntExtra("producto_position", -1)
        planId = intent.getStringExtra("planId")
        val productoList = ProductoListSingleton.obtenerProductos()

        val btnContinuar = findViewById<Button>(R.id.btn_agregar_compartido)
        val ctnBackProfile = findViewById<ImageView>(R.id.back_btn_profile)

        // Verificar si la lista de productos no es nula y si la posición es válida
        if (productoList != null) {
            if (productoPosition >= 0 && productoPosition < productoList.size) {
                producto = productoList[productoPosition]

                // Recuperar vistas
                val txtTitulo = findViewById<TextView>(R.id.txt_nombre_cart)
                val txtDescripcion = findViewById<TextView>(R.id.txt_item_descripcion_cart)
                val txtPrecio = findViewById<TextView>(R.id.precio_x_unidad_item_cart)
                val txtPrecioXCantidad = findViewById<TextView>(R.id.txt_cantidad_x_precio_cart)

                txtTitulo.text = producto.titulo
                txtDescripcion.text = producto.descripcion
                txtPrecio.text = "$${producto.precio}"
                txtPrecioXCantidad.text = "$${producto.precio}"

                // Configurar el botón Continuar
                btnContinuar.setOnClickListener {
                    VibrateUtil.vibrar(this,200)
                    btnContinuar.isEnabled = false

                    Handler(Looper.getMainLooper()).postDelayed({
                        btnContinuar.isEnabled = true
                    }, 3000)

                    mp.enviarSolicitud(producto.id, planId)

                }

                // Acción para volver al perfil
                ctnBackProfile.setOnClickListener {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            } else {
                Toast.makeText(this, "Producto no válido", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}
