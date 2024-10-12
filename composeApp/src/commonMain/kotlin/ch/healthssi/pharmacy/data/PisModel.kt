package ch.healthssi.pharmacy.data

import ch.healthssi.pharmacy.di.PIS_SERVER
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestPrescription(
    val responseMode: String = "direct_post",
    val authorizeBaseUrl: String = "openid4vp://authorize",
    val statusCallbackUri: String = "https://$PIS_SERVER/vp/status",
    val presentationDefinition: PresentationDefinition = PresentationDefinition()
)

@Serializable
data class PresentationDefinition(
    @SerialName("request_credentials")
    val requestCredentials: List<CredentialDescriptor> = listOf(
        CredentialDescriptor(
            "jwt_vc_json",
            "SwissMedicalPrescription"
        )
    )
)

@Serializable
data class CredentialDescriptor(
    val format: String,
    val type: String
)
