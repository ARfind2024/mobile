package com.liontail.arfind.fragments

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.liontail.arfind.R

class MapFragment : Fragment() {

    private val callback = OnMapReadyCallback { googleMap ->

        val buenosAires = LatLng(-34.6037, -58.3816)  // Coordenadas de Buenos Aires
        googleMap.addMarker(MarkerOptions().position(buenosAires).title("Marker in Buenos Aires"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(buenosAires, 10f))  // Ajusta el zoom si es necesario
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
}