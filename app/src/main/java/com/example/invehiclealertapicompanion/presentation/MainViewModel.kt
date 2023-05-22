package com.example.invehiclealertapicompanion.presentation

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.invehiclealertapicompanion.R
import com.example.invehiclealertapicompanion.presentation.health.HealthServicesManager
import com.example.invehiclealertapicompanion.presentation.health.MeasureMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val healthServicesManager: HealthServicesManager
) : ViewModel() {
    val greetingName: String
        get() = "User"

    private val _iconRes = MutableStateFlow(R.drawable.ic_broken_heart)
    val iconRes: StateFlow<Int> = _iconRes

    private val _uiStateDes = MutableStateFlow(UiState.Startup.des)
    val uiStateDes: StateFlow<String> = _uiStateDes

    private val _heartRateBpm = MutableStateFlow(0.0)
    val heartRateBpm: StateFlow<Double> = _heartRateBpm
    init {
        // Check that the device has the heart rate capability and progress to the next state
        // accordingly.
        viewModelScope.launch {
            _uiStateDes.value = if (healthServicesManager.hasHeartRateCapability()) {
                UiState.HeartRateAvailable.des
            } else {
                UiState.HeartRateNotAvailable.des
            }
        }
    }

    fun start() {
        TODO("Not yet implemented")
    }

    @ExperimentalCoroutinesApi
    suspend fun measureHeartRate() {
        healthServicesManager.heartRateMeasureFlow().collect {
            when (it) {
                is MeasureMessage.MeasureAvailability -> {
                    Log.d(TAG, "Availability changed: ${it.availability}")
//                    _heartRateAvailable.value = it.availability
                    _iconRes.value = R.drawable.ic_broken_heart
                }

                is MeasureMessage.MeasureData -> {
                    val bpm = it.data.last().value
                    Log.d(TAG, "Data update: $bpm")
                    _heartRateBpm.value = bpm
                    _iconRes.value = R.drawable.ic_heart
                }
            }
        }
    }
}

sealed class UiState {
    abstract val des: String

    object Startup : UiState() {
        override val des: String
            get() = "Started"
    }

    object HeartRateAvailable : UiState() {
        override val des: String
            get() = "Available"
    }

    object HeartRateNotAvailable : UiState() {
        override val des: String
            get() = "Unavailable"
    }
}
