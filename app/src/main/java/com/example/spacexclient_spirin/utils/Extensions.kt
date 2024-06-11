package com.example.spacexclient_spirin.utils

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.TRANSPORT_BLUETOOTH
import android.net.NetworkCapabilities.TRANSPORT_CELLULAR
import android.net.NetworkCapabilities.TRANSPORT_ETHERNET
import android.net.NetworkCapabilities.TRANSPORT_VPN
import android.net.NetworkCapabilities.TRANSPORT_WIFI
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import com.example.spacexclient_spirin.presentation.SpaceXApplication
import com.example.spacexclient_spirin.utils.Constants.SETTINGS_STORE_NAME


fun Fragment.appComponent() = (activity?.application as SpaceXApplication).appComponent!!

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(SETTINGS_STORE_NAME)

val Context.isNetworkConnected: Boolean
    get() {
        val manager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        return manager.getNetworkCapabilities(manager.activeNetwork)?.let {
            it.hasTransport(TRANSPORT_WIFI) || it.hasTransport(TRANSPORT_CELLULAR) ||
                    it.hasTransport(TRANSPORT_BLUETOOTH) ||
                    it.hasTransport(TRANSPORT_ETHERNET) ||
                    it.hasTransport(TRANSPORT_VPN)
        } ?: false
    }