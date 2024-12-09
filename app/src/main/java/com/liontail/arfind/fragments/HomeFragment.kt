package com.liontail.arfind.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.liontail.arfind.R
import com.liontail.arfind.api.ApiService
import com.liontail.arfind.api.RetrofitClient
import com.liontail.arfind.firebase.singleton.DispositivoInvitadosListSingleton
import com.liontail.arfind.firebase.singleton.DispositivoListSingleton
import com.liontail.arfind.firebase.singleton.ProductoListSingleton
import com.liontail.arfind.fragments.adapater.DispositivoAdapter
import com.liontail.arfind.fragments.adapater.DispositivoCompartidosAdapter
import com.liontail.arfind.fragments.adapater.ProductoAdapter

class HomeFragment : Fragment() {

    private var recyclerViewProductos: RecyclerView? = null
    private var recyclerViewDispositivos: RecyclerView? = null
    private var recyclerViewDispositivosInvitados: RecyclerView? = null

    private var productoAdapter: ProductoAdapter? = null
    private var dispositivoAdapter: DispositivoAdapter? = null
    private var dispositivoCompartidosAdapter: DispositivoCompartidosAdapter? = null

    // Inicializamos el servicio API
    private val apiService: ApiService = RetrofitClient.getRetrofitInstance()
        .create(ApiService::class.java)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Inicializamos las vistas
        recyclerViewProductos = view.findViewById(R.id.recycler_productos)
        recyclerViewDispositivos = view.findViewById(R.id.recycler_dispositivos)
        recyclerViewDispositivosInvitados = view.findViewById(R.id.recycler_dispositivos_invitados)
        // Configuración y carga de los productos en el RecyclerView
        recyclerViewProductos?.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            cargarProductos()
        }
        // Configuración y carga de los dispositivos en el RecyclerView
        recyclerViewDispositivos?.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            cargarDispositivos()

        }
        // Configuración y carga de los dispositivos en el RecyclerView
        recyclerViewDispositivosInvitados?.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            cargarDispositivosInvitados()
        }
        return view
    }
    // Función para cargar los productos
    private fun cargarProductos() {
        val productos = ProductoListSingleton.getInstance()

        // Verificamos si la lista de productos está disponible y configuramos el adaptador
        productos?.let {
            productoAdapter = ProductoAdapter(
                requireActivity(),
                it.lproductosDtos // Se obtiene la lista de productos
            )
            recyclerViewProductos?.adapter = productoAdapter
        } ?: run {

        }
    }
    private fun cargarDispositivos() {
        val dispositivos = DispositivoListSingleton.getInstance()
        dispositivos?.let { dispositivoList ->

            dispositivoAdapter = DispositivoAdapter(
                requireActivity(),
                dispositivoList.dispositivosDtos,
                apiService
            )
            // Asignamos el adaptador al RecyclerView
            recyclerViewDispositivos?.adapter = dispositivoAdapter

        } ?: run {
        }
    }

    private fun cargarDispositivosInvitados() {
        val dispositivos = DispositivoInvitadosListSingleton.getInstance()
        dispositivos?.let { dispositivoList ->

            dispositivoCompartidosAdapter = DispositivoCompartidosAdapter(
                requireActivity(),
                dispositivoList.dispositivosDtos
            )
            recyclerViewDispositivosInvitados?.adapter = dispositivoCompartidosAdapter

        } ?: run {
        }
    }

}
