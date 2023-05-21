package com.example.invehiclealertapicompanion.presentation.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import com.example.invehiclealertapicompanion.R
import com.example.invehiclealertapicompanion.presentation.MainViewModel
import com.example.invehiclealertapicompanion.presentation.theme.InVehicleAlertAPICompanionTheme

@Composable
fun WearApp(viewModel: MainViewModel) {
    InVehicleAlertAPICompanionTheme {
        /* If you have enough items in your list, use [ScalingLazyColumn] which is an optimized
         * version of LazyColumn for wear devices with some added features. For more information,
         * see d.android.com/wear/compose.
         */
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background),
            verticalArrangement = Arrangement.Center
        ) {
            val heartRateBpm = viewModel.heartRateBpm.collectAsState().value
            val uiState = viewModel.uiState.collectAsState().value


            Greeting(greetingName = viewModel.greetingName)
            Button(modifier = Modifier.align(Alignment.CenterHorizontally), onClick = {viewModel.start()}) {
                Text(text = "Start")
            }
        }
    }
}

@Composable
fun Greeting(greetingName: String) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        color = MaterialTheme.colors.primary,
        text = stringResource(R.string.hello_world, greetingName)
    )
}

@Preview(device = Devices.WEAR_OS_SMALL_ROUND, showSystemUi = true)
@Composable
fun DefaultPreview() {
//    WearApp()
}