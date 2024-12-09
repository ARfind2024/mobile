package com.liontail.arfind

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.liontail.arfind.api.RetrofitClient
import com.liontail.arfind.fragments.HomeFragment
import com.liontail.arfind.fragments.MapFragment
import com.liontail.arfind.fragments.ShareFragment
import com.liontail.arfind.utils.NetworkUtils

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        RetrofitClient.initialize(this)
        setContentView(R.layout.activity_main)

        if (!NetworkUtils.isNetworkAvailable(this)) {
            NetworkUtils.redirectToNoInternetActivity(this)
        }

        // Configurar DrawerLayout
        drawerLayout = findViewById(R.id.drawer_layout)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Configurar el icono de la hamburguesa
        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.abrir_menu,
            R.string.cerrar_menu
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Configurar BottomNavigationView
        bottomNav = findViewById(R.id.bottom_navigation_view)
        bottomNav.setOnItemSelectedListener { item -> handleItemSelected(item.itemId) }

        // Configurar el clic en la campana
        val linearCampana = findViewById<ImageView>(R.id.linear_campana)
        linearCampana.setOnClickListener {
            val intent = Intent(this, NotificacionActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Si es la primera vez, mostrar el fragment HomeFragment
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment())
                .commit()
        }

        // Configurar NavigationView
        val navigationView: NavigationView = findViewById(R.id.navigation_view)
        navigationView.setNavigationItemSelectedListener { item ->
            handleNavigationItemSelected(item.itemId)
            drawerLayout.closeDrawer(Gravity.LEFT) // Cerrar el menú después de seleccionar una opción
            true
        }
    }

    private fun handleItemSelected(itemId: Int): Boolean {
        val selectedFragment = when (itemId) {
            R.id.action_home -> HomeFragment()
            R.id.action_pedidos -> MapFragment()
            R.id.action_agregar -> ShareFragment()
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

    private fun handleNavigationItemSelected(itemId: Int) {
        when (itemId) {
            R.id.nav_item_1 -> {
                // ver perfil
                val intent = Intent(this, PerfilActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.nav_item_2 -> {
                AlertDialog.Builder(this)
                    .setMessage("¿Estás seguro de que deseas salir?")
                    .setCancelable(false)
                    .setPositiveButton("Sí") { _, _ ->
                        super.onBackPressedDispatcher.onBackPressed()
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    .setNegativeButton("No", null)
                    .show()
            }
        }
    }

    fun switchToFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null)  // Esto asegura que puedes volver al fragment anterior
        transaction.commit()
    }

    @SuppressLint("MissingSuperCall")
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
    }


}
