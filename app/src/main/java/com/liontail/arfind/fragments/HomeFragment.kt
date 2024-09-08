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
import com.liontail.arfind.fragments.adapater.PlanAdapter

class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private var recyclerViewPlanes: RecyclerView? = null
    private var planAdapter: PlanAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_home, container, false)

        recyclerViewPlanes = view?.findViewById(R.id.recycler_planes)

        recyclerViewPlanes?.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            cargarPlanes()
        }
        return view

    }



    private fun cargarPlanes() {
        val planes = ProductoListSingleton.getInstance()

        if (planes != null) {
            planAdapter = PlanAdapter(requireActivity(), planes.lplanDtos)
            recyclerViewPlanes!!.adapter = planAdapter
        } else {
        }
    }

}