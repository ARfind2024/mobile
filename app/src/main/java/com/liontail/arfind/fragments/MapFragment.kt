package com.liontail.arfind.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.liontail.arfind.R
import com.liontail.arfind.dispositivos.DispositivoDto
import com.liontail.arfind.firebase.singleton.DispositivoUnifiedListSingleton

class MapFragment : Fragment() {

    private lateinit var googleMap: GoogleMap
    private lateinit var countryPanel: LinearLayout

    private val callback = OnMapReadyCallback { map ->
        googleMap = map

        val buenosAires = LatLng(-34.6037, -58.3816)  // Coordenadas de Buenos Aires
        googleMap.addMarker(MarkerOptions().position(buenosAires).title("Marker in Buenos Aires"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(buenosAires, 50f))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configurar el mapa
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        // Configurar el panel
        countryPanel = view.findViewById(R.id.country_panel)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)

        // Filtrar países con location no nula y pasar al adaptador
        val filteredCountries = getCountries()?.filter { it.location.latitude != 0.0 && it.location.longitude != 0.0 }

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = CountryAdapter(filteredCountries ?: emptyList()) { country ->
            // Mover el mapa a la ubicación seleccionada
            googleMap.addMarker(MarkerOptions().position(country.location).title(country.name))
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(country.location, 100f))
        }

        // Mostrar el panel al iniciar (puedes añadir lógica según sea necesario)
        countryPanel.visibility = View.VISIBLE
    }

    private fun getCountries(): List<Country>? {
        return getAllDispositivos()?.let { parseDispositivoToCountry(it) }
    }

    private fun getAllDispositivos(): List<DispositivoDto>? {
        return DispositivoUnifiedListSingleton.obtenerListaUnificada()
    }

    private fun parseDispositivoToCountry(dispositivos: List<DispositivoDto>): List<Country> {
        return dispositivos.map { dispositivo ->

            val ubicacion = dispositivo.ubicacion

            val location = if (ubicacion != null && ubicacion.latitude != 0.0 && ubicacion.longitude != 0.0) {
                Log.d("MapFragment", "Dispositivo: ${dispositivo.apodo}, Latitud: ${ubicacion.latitude}, Longitud: ${ubicacion.longitude}")
                LatLng(ubicacion.latitude, ubicacion.longitude)
            } else {
                Log.w("MapFragment", "Ubicación inválida para ${dispositivo.apodo}, usando coordenadas predeterminadas")
                LatLng(0.0, 0.0)  // Puedes usar una ubicación predeterminada si es necesario
            }

            Country(
                name = dispositivo.apodo,
                location = location
            )
        }
    }

    data class Country(
        val name: String,
        val location: LatLng
    )

    class CountryAdapter(
        private val countries: List<Country>,
        private val onItemClick: (Country) -> Unit
    ) : RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {

        class CountryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val nameTextView: TextView = itemView.findViewById(R.id.text_country_name)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_country_card, parent, false)
            return CountryViewHolder(view)
        }

        override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
            val country = countries[position]
            holder.nameTextView.text = country.name
            holder.itemView.setOnClickListener { onItemClick(country) }
        }

        override fun getItemCount() = countries.size
    }
}
