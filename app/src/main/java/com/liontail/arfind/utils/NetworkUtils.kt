package com.liontail.arfind.utils

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.liontail.arfind.NoInternetActivity


object NetworkUtils {

    fun redirectToNoInternetActivity(context: Context) {
        val intent = Intent(context, NoInternetActivity::class.java)
        context.startActivity(intent)
    }

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        return connectivityManager?.let { cm ->
            val network = cm.activeNetwork ?: return false
            val networkCapabilities = cm.getNetworkCapabilities(network) ?: return false
            networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        } ?: false
    }
}