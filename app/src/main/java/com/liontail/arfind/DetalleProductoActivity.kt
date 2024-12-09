package com.liontail.arfind

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.liontail.arfind.api.ApiService
import com.liontail.arfind.api.RetrofitClient
import com.liontail.arfind.firebase.singleton.ProductoListSingleton
import com.liontail.arfind.mercadopago.MPCheckout
import com.liontail.arfind.planes.PlanesAdapter
import com.liontail.arfind.planes.PlanesColeccion
import com.liontail.arfind.utils.VibrateUtil
import com.squareup.picasso.Picasso

class DetalleProductoActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_producto)

        val apiService: ApiService = RetrofitClient.getRetrofitInstance()
            .create(ApiService::class.java)
        val productoPosition = intent.getIntExtra("producto_position", -1)
        val recyclerPlanes = findViewById<RecyclerView>(R.id.recycler_planes)
        var planId = ""
        var planPrecio = 0.0
        var productoPrecio = 0.0
        var txtPrecioXCantidad = findViewById<TextView>(R.id.txt_cantidad_x_precio)
        val mp = MPCheckout(this@DetalleProductoActivity,apiService)
        val btnContinuar = findViewById<Button>(R.id.btn_agregar_compartido)
        val txtTotal = findViewById<TextView>(R.id.txt_total_a_pagar)


        btnContinuar.isEnabled = false

        recyclerPlanes.layoutManager = LinearLayoutManager(this)

        if (productoPosition == -1) {
            Toast.makeText(this, "Producto no encontrado", Toast.LENGTH_SHORT).show()
            finish()
            return
        }


        val productoList = ProductoListSingleton.obtenerProductos()

        if (productoList != null) {
            if (productoPosition >= 0 && productoPosition < productoList.size) {
                val producto = productoList[productoPosition]

                // Recuperar vistas

                val ctnBackProfile = findViewById<ImageView>(R.id.back_btn_profile)
                val imagenProducto = findViewById<ImageView>(R.id.img_reserva)
                val txtTitulo = findViewById<TextView>(R.id.nombre_producto)
                val txtDescripcion = findViewById<TextView>(R.id.descripcion_producto)
                val txtPrecioProducto = findViewById<TextView>(R.id.precio_producto)


                txtTitulo.text = producto.titulo
                txtDescripcion.text = producto.descripcion
                productoPrecio = producto.precio
                txtPrecioProducto.text = producto.precio.toString()

                txtTotal.text = "Producto $$productoPrecio +  Plan $$planPrecio"

                txtPrecioXCantidad.text = "$${planPrecio + productoPrecio}"


                // Cargar la imagen del producto con Picasso
                val imageUrl = producto.imagen
                if (imageUrl != null && imageUrl.isNotEmpty()) {
                    Picasso.get().load(imageUrl).into(imagenProducto)
                } else {
                    imagenProducto.setImageResource(R.drawable.img_dispositivo_back)
                }

                // Acciones de botones
                btnContinuar.setOnClickListener {
                    VibrateUtil.vibrar(this,200)
                    btnContinuar.isEnabled = false


                    Handler(Looper.getMainLooper()).postDelayed({
                        btnContinuar.isEnabled = true
                    }, 3000)

                    mp.enviarSolicitud(producto.id, planId)


                }

                ctnBackProfile.setOnClickListener {
                    val intent = Intent(this, MainActivity::class.java)
                    VibrateUtil.vibrar(this,200)
                    btnContinuar.isEnabled = false
                    ctnBackProfile.isEnabled = false
                    startActivity(intent)
                    finish()
                }
            } else {
                // Si la posición es inválida, mostramos un mensaje de error
                Toast.makeText(this, "Producto no válido", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        PlanesColeccion.obtenerPlanesAsync().thenAccept { planes ->
            runOnUiThread {
                val adapter = PlanesAdapter(planes) { selectedPlan ->
                    //Toast.makeText(this, "Plan seleccionado: ${selectedPlan.id}", Toast.LENGTH_SHORT).show()
                    planId = selectedPlan.id
                    planPrecio = selectedPlan.precio
                    txtPrecioXCantidad.text = "$${planPrecio + productoPrecio}"
                    txtTotal.text = "Producto $$productoPrecio +  Plan $$planPrecio"
                    btnContinuar.isEnabled = true
                }
                recyclerPlanes.adapter = adapter
            }
        }.exceptionally { throwable ->
            runOnUiThread {
                // Toast.makeText(this, "Error al obtener planes: ${throwable.message}", Toast.LENGTH_SHORT).show()
            }
            null
        }

    }
}
