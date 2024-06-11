package com.example.spacexclient_spirin.presentation.settings

import android.content.res.Resources
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spacexclient_spirin.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch


class SettingsViewModel : ViewModel() {
    private val _state = MutableStateFlow(
        SwitchesState(
            height = "",
            diameter = "",
            mass = "",
            payloadWeights = ""
        )
    )
    val state: StateFlow<SwitchesState> = _state

    fun getInitState(resources: Resources, dataStore: DataStore<Preferences>) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.data.take(1).collect {
                _state.emit(
                    SwitchesState(
                        it[HEIGHT] ?: resources.getString(R.string.m),
                        it[DIAMETER] ?: resources.getString(R.string.m),
                        it[MASS] ?: resources.getString(R.string.kg),
                        it[PAYLOAD_WEIGHTS] ?: resources.getString(R.string.kg)
                    )
                )
            }
        }
    }

    fun updateDataStoreField(dataStore: DataStore<Preferences>, parameter: Preferences.Key<String>, state: String) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.edit { it[parameter] = state }
        }
    }

    data class SwitchesState(
        val height: String,
        val diameter: String,
        val mass: String,
        val payloadWeights: String
    )

    companion object {
        val HEIGHT = stringPreferencesKey("height")
        val DIAMETER = stringPreferencesKey("diameter")
        val MASS = stringPreferencesKey("mass")
        val PAYLOAD_WEIGHTS = stringPreferencesKey("payloadWeights")
    }

}