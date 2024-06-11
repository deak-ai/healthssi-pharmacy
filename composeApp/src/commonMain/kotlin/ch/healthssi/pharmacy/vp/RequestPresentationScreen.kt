package ch.healthssi.pharmacy.vp

import androidx.compose.runtime.*
import io.ktor.client.*
import org.koin.compose.koinInject

@Composable
fun RequestPresentationScreen(
) {
    val rpViewModel = koinInject<RequestPresentationViewModel>()
    val state by rpViewModel.state.collectAsState()
    when (state) {
        is VPState.Initial -> InitialScreen(rpViewModel)
        is VPState.QRDisplay -> RotatingQrDisplayScreen(rpViewModel, PrescriptionViewModel(
            (state as VPState.QRDisplay).qrUrl,
            koinInject<HttpClient>())
        )
    }
}