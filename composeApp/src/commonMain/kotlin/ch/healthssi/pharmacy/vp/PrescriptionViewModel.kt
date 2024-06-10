package ch.healthssi.pharmacy.vp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ktor.client.*
import io.ktor.client.plugins.websocket.*
import io.ktor.http.*
import io.ktor.websocket.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

class PrescriptionViewModel(
    private val qrUrl: Url
): ViewModel() {

    private val _webSocketMessage = MutableStateFlow("")
    val webSocketMessage: StateFlow<String> = _webSocketMessage

    private val httpClient = koinInject<HttpClient>()

    init {
        viewModelScope.launch {

            httpClient.ws(
                method = HttpMethod.Get,
                host = "localhost",
                port = 8081,
                path = "/notifications"
            ) {
                incoming.consumeAsFlow().collect { frame ->
                    if (frame is Frame.Text) {
                        val receivedText = frame.readText()
                        _webSocketMessage.value = receivedText
                    }
                }
            }
        }
    }

}