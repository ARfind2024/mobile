package com.liontail.arfind.fragments
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.liontail.arfind.R
import com.liontail.arfind.firebase.singleton.ProductoListSingleton
import com.liontail.arfind.fragments.adapater.ProductoAdapter

class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private var recyclerViewProductos: RecyclerView? = null
    private var productoAdapter: ProductoAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_home, container, false)

        recyclerViewProductos = view?.findViewById(R.id.recycler_productos)

        recyclerViewProductos?.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            cargarProductos()
        }
        return view

    }



    private fun cargarProductos() {
        val planes = ProductoListSingleton.getInstance()

        if (planes != null) {
            productoAdapter = ProductoAdapter(
                requireActivity(),
                planes.lproductosDtos
            )
            recyclerViewProductos!!.adapter = productoAdapter
        } else {
        }
    }

}