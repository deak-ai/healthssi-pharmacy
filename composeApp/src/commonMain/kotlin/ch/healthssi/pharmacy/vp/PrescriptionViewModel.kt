package ch.healthssi.pharmacy.vp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.healthssi.pharmacy.data.PrescriptionData
import io.ktor.client.*
import io.ktor.client.plugins.websocket.*
import io.ktor.http.*
import io.ktor.websocket.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.koin.compose.koinInject

class PrescriptionViewModel(
    private val qrUrl: Url,
    private val httpClient: HttpClient
): ViewModel() {

    private val _webSocketMessage = MutableStateFlow("")
    val webSocketMessage: StateFlow<String> = _webSocketMessage

    init {
        viewModelScope.launch {
            val stateId = qrUrl.parameters["state"]
            println("Initialising websocket for $stateId")
            httpClient.wss(
                method = HttpMethod.Get,
                host = "pis.healthwallet.li",
                port = 443,
                path = "/notifications/$stateId"
            ) {
                incoming.consumeAsFlow().collect { frame ->
                    if (frame is Frame.Text) {
                        val receivedText = frame.readText()
                        _webSocketMessage.value = deserialize(receivedText)
                    }
                }
            }
        }
    }

    private val json = Json { ignoreUnknownKeys = true }

    private fun deserialize(message: String): String {
        try {
            val pd = json.decodeFromString<PrescriptionData>(message)

            val pdf = """
                SWISS MEDICAL PRESCRIPTION
                
                Issued: ${pd.issuanceDate}
                Expires: ${pd.expirationDate}
                Id: ${pd.prescriptionId}
                Valid: ${pd.verificationSuccess}
               
                Patient First Name: ${pd.patientFirstName}
                Patient Last Name: ${pd.patientLastName}
                Patient Birth Date: ${pd.patientBirthDate}
               
                Doctor ID: ${pd.doctor}
                
                Med: ${pd.medicationRefData?.nameDe}
                By: ${pd.medicationRefData?.authHolderName}

            """.trimIndent()

            return pdf

        } catch (e: Exception) {
            println("Failed to deserialize prescription data: $e")
        }

        return message

    }



}