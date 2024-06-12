package ch.healthssi.pharmacy.vp

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.healthssi.pharmacy.data.RequestPrescription
import ch.healthssi.pharmacy.repo.PisServerRepository
import io.ktor.client.*
import io.ktor.http.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.jetbrains.skia.Image


sealed class VPState {
    data object Initial : VPState()
    data class QRDisplay(val qrUrl: Url) : VPState()
}

sealed class VPEvent {
    data class QRObtained(val qrUrl: Url) : VPEvent()
    data object Reset : VPEvent()
}

fun ByteArray.toImageBitmap(): ImageBitmap {
    val skiaImage = Image.makeFromEncoded(this)
    return skiaImage.toComposeImageBitmap()
}

class RequestPresentationViewModel(
    private val pisServerRepos: PisServerRepository,
    private val httpClient: HttpClient):ViewModel() {

    private val _state = MutableStateFlow<VPState>(VPState.Initial)
    val state: StateFlow<VPState> = _state

    private val _qrString = MutableStateFlow<String>("")
    val qrString: StateFlow<String> = _qrString

    private val _qrCodeImage = MutableStateFlow<ImageBitmap?>(null)
    val qrCodeImage: StateFlow<ImageBitmap?> = _qrCodeImage


    fun handleEvent(event: VPEvent) {
        when (event) {
            // common events
            is VPEvent.QRObtained ->
                _state.value = VPState.QRDisplay((event as VPEvent.QRObtained).qrUrl)
            is VPEvent.Reset -> {
                _state.value = VPState.Initial
                _qrString.value = ""
                _qrCodeImage.value = null
//                _webSocketMessage.value = ""
            }
        }
    }

    fun requestPresentation() {
        viewModelScope.launch {
            try {
                val qrUrl = pisServerRepos.createPrescriptionPresentationRequest(RequestPrescription()).getOrThrow()
                val qrString = qrUrl.toString()
                _qrString.value = qrString

                val qrBytes = pisServerRepos.generateQrCode(qrString).getOrThrow()
                _qrCodeImage.value = qrBytes.toImageBitmap()

                handleEvent(VPEvent.QRObtained(qrUrl))
            } catch (e: Exception) {
                println(e)
            }
        }
    }

}