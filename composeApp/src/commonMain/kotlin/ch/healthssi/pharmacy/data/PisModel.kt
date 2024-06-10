package ch.healthssi.pharmacy.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestPrescription(
    val responseMode: String = "direct_post",
    val authorizeBaseUrl: String = "openid4vp://authorize",
    val statusCallbackUri: String = "https://pis.healthwallet.li/vp/status",
    val presentationDefinition: PresentationDefinition = PresentationDefinition()
)

@Serializable
data class PresentationDefinition(
    @SerialName("request_credentials")
    val requestCredentials: List<String> = listOf("SwissMedicalPrescription")
)
