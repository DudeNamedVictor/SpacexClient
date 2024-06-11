package com.example.spacexclient_spirin.presentation.launches

import android.annotation.SuppressLint
import android.content.res.Resources
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.spacexclient_spirin.R
import com.example.spacexclient_spirin.domain.entities.LaunchesEntity
import com.example.spacexclient_spirin.domain.usecases.GetLaunchesUseCase
import com.example.spacexclient_spirin.presentation.base.BaseViewModel
import com.example.spacexclient_spirin.utils.DataParser
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import javax.inject.Provider


class LaunchesViewModel @Inject constructor(
    private val useCase: GetLaunchesUseCase,
    private val resources: Resources
) : BaseViewModel() {

    override val exceptionHandler: CoroutineExceptionHandler = CoroutineExceptionHandler { _, _ ->
        launch {
            _state.emit(ScreenState.Error)
        }
    }

    fun getLaunches(rocketId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.invoke(rocketId)
                .onSuccess {
                    _state.emit(ScreenState.Success(prepareLaunches(it)))
                }.onFailure {
                    _state.emit(ScreenState.Error)
                }
        }
    }

    private fun prepareLaunches(launchesEntity: List<LaunchesEntity>) = launchesEntity.map {
        LaunchesUIState(
            missionName = it.missionName,
            launchDate = formatDate(it.launchDateUnix),
            launchImage = prepareImage(it.isSuccessLaunch)
        )
    }

    private fun formatDate(date: Long): String {
        val utcData = DateTimeFormatter.ISO_INSTANT.format(java.time.Instant.ofEpochSecond(date))

        return DataParser(resources.getStringArray(R.array.months)).parseData(
            utcData.substring(
                0,
                10
            )
        )
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun prepareImage(launchState: Boolean) = if (launchState) {
        resources.getDrawable(R.drawable.rocket_success_24, null)
    } else {
        resources.getDrawable(R.drawable.rocket_unsuccessful_24, null)
    }

    class LaunchesViewModelFactory @Inject constructor(
        myViewModelProvider: Provider<LaunchesViewModel>
    ) : ViewModelProvider.Factory {
        private val providers = mapOf<Class<*>, Provider<out ViewModel>>(
            LaunchesViewModel::class.java to myViewModelProvider
        )

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return providers[modelClass]!!.get() as T
        }
    }

}