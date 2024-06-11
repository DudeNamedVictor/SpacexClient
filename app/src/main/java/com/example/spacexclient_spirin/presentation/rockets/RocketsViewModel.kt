package com.example.spacexclient_spirin.presentation.rockets

import android.content.res.Resources
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.spacexclient_spirin.R
import com.example.spacexclient_spirin.domain.entities.RocketsEntity
import com.example.spacexclient_spirin.domain.entities.RocketsParameters
import com.example.spacexclient_spirin.domain.usecases.GetRocketsUseCase
import com.example.spacexclient_spirin.presentation.base.BaseViewModel
import com.example.spacexclient_spirin.presentation.settings.SettingsViewModel
import com.example.spacexclient_spirin.utils.DataParser
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider


class RocketsViewModel @Inject constructor(
    private val useCase: GetRocketsUseCase,
    private val resources: Resources
) : BaseViewModel() {

    override val exceptionHandler: CoroutineExceptionHandler = CoroutineExceptionHandler { _, _ ->
        launch {
            _state.emit(ScreenState.Error)
        }
    }
    private var switchesState = mutableListOf<String>()

    fun prepareScreen(dataStore: DataStore<Preferences>) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                launch { getParametersState(dataStore) }.join()
                launch { getRockets() }
            } catch (ce: CancellationException) {
                throw ce
            } catch (e: Exception) {
                _state.emit(ScreenState.Error)
            }
        }
    }

    private suspend fun getParametersState(dataStore: DataStore<Preferences>) {
        switchesState.clear()
        dataStore.data.take(1).collect {
            switchesState.add(it[SettingsViewModel.HEIGHT] ?: resources.getString(R.string.m))
            switchesState.add(it[SettingsViewModel.DIAMETER] ?: resources.getString(R.string.m))
            switchesState.add(it[SettingsViewModel.MASS] ?: resources.getString(R.string.kg))
            switchesState.add(it[SettingsViewModel.PAYLOAD_WEIGHTS] ?: resources.getString(R.string.kg))
        }
    }

    private suspend fun getRockets() {
        try {
            useCase.invoke().collect { rocketsList ->
                _state.emit(ScreenState.Success(prepareRockets(rocketsList)))
            }
        } catch (ce: CancellationException) {
            throw ce
        } catch (e: Exception) {
            _state.emit(ScreenState.Error)
        }
    }

    private fun prepareRockets(rocketsEntity: List<RocketsEntity>): List<RocketsUIState> =
        rocketsEntity.map {
            RocketsUIState(
                rocketId = it.rocketId,
                rocketName = it.rocketName,
                parameters = prepareParameters(it.parameters),
                firstFlight = parseData(it.firstFlight),
                country = it.country,
                costPerLaunch = formatCost(it.costPerLaunch),
                firstStage = RocketsEntityStageInfoUI(
                    it.firstStage.engines,
                    addStageUnits(it.firstStage.fuelAmount, R.string.ton),
                    addStageUnits(it.firstStage.burnTime, R.string.sec)
                ),
                secondStage = RocketsEntityStageInfoUI(
                    it.secondStage.engines,
                    addStageUnits(it.secondStage.fuelAmount, R.string.ton),
                    addStageUnits(it.secondStage.burnTime, R.string.sec)
                ),
                flickrImages = it.flickrImages
            )
        }

    private fun prepareParameters(parameters: List<RocketsParameters>): List<RocketsParametersUI> {
        val readyParameters = mutableListOf<RocketsParametersUI>()
        for (i in switchesState.indices) {
            if ((switchesState.size > i + 1) && i == 0) {
                val value = if (switchesState[i] == resources.getString(R.string.m)) {
                    parameters[i].firstValue
                } else {
                    parameters[i].secondValue
                }
                val unit = if (switchesState[i] == resources.getString(R.string.m)) {
                    resources.getString(R.string.height_meters)
                } else {
                    resources.getString(R.string.height_feet)
                }
                readyParameters.add(RocketsParametersUI(value, unit))
            } else if ((switchesState.size > i + 1) && i == 1) {
                val value = if (switchesState[i] == resources.getString(R.string.m)) {
                    parameters[i].firstValue
                } else {
                    parameters[i].secondValue
                }
                val unit = if (switchesState[i] == resources.getString(R.string.m)) {
                    resources.getString(R.string.diameter_meters)
                } else {
                    resources.getString(R.string.diameter_feet)
                }
                readyParameters.add(RocketsParametersUI(value, unit))
            } else if ((switchesState.size > i + 1) && i == 2) {
                val value = if (switchesState[i] == resources.getString(R.string.kg)) {
                    parameters[i].firstValue
                } else {
                    parameters[i].secondValue
                }
                val unit = if (switchesState[i] == resources.getString(R.string.kg)) {
                    resources.getString(R.string.mass_kg)
                } else {
                    resources.getString(R.string.mass_lb)
                }
                readyParameters.add(RocketsParametersUI(value, unit))
            } else if ((switchesState.size > i + 1) && i == 3) {
                val value = if (switchesState[i] == resources.getString(R.string.kg)) {
                    parameters[i].firstValue
                } else {
                    parameters[i].secondValue
                }
                val unit = if (switchesState[i] == resources.getString(R.string.kg)) {
                    resources.getString(R.string.payload_weights_kg)
                } else {
                    resources.getString(R.string.payload_weights_lb)
                }
                readyParameters.add(RocketsParametersUI(value, unit))
            }
        }

        return readyParameters
    }

    private fun parseData(data: String) =
        DataParser(resources.getStringArray(R.array.months)).parseData(data)

    private fun formatCost(cost: Int) =
        "$ " + (cost / SUM_FOR_FORMAT_COST).toString() + " " + resources.getString(R.string.mln)

    private fun addStageUnits(value: String, units: Int): SpannableString {
        val spannableString = SpannableString("$value ${resources.getString(units)}")
        spannableString.setSpan(
            ForegroundColorSpan(
                resources.getColor(R.color.gray, null)
            ),
            value.length + 1,
            spannableString.length,
            Spanned.SPAN_EXCLUSIVE_INCLUSIVE
        )

        return spannableString
    }

    class RocketsViewModelFactory @Inject constructor(
        myViewModelProvider: Provider<RocketsViewModel>
    ) : ViewModelProvider.Factory {
        private val providers = mapOf<Class<*>, Provider<out ViewModel>>(
            RocketsViewModel::class.java to myViewModelProvider
        )

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return providers[modelClass]!!.get() as T
        }
    }

    companion object {
        private const val SUM_FOR_FORMAT_COST = 1000000
    }

}