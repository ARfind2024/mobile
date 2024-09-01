package com.liontail.arfind

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.liontail.arfind.fragments.HomeFragment
import com.liontail.arfind.fragments.MapFragment
import com.liontail.arfind.utils.NetworkUtils

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (!NetworkUtils.isNetworkAvailable(this)) {
            NetworkUtils.redirectToNoInternetActivity(this)
        }

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        bottomNav.setOnItemSelectedListener { item -> handleItemSelected(item.itemId) }


        val  linearCampana = findViewById<LinearLayout>(R.id.linear_campana)

        linearCampana.setOnClickListener{
            val intent = Intent(this, NotificacionActivity::class.java)
            startActivity(intent)
            finish()
        }
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment())
                .commit()
        }

    }

    private fun handleItemSelected(itemId: Int): Boolean {
        val selectedFragment = when (itemId) {
            R.id.action_home -> HomeFragment()
            R.id.action_pedidos -> MapFragment()
            //R.id.action_perfil -> MasFragment()
            else -> null
        }
        selectedFragment?.let {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, it)
                .commit()
            return true
        }
        return false
    }




}