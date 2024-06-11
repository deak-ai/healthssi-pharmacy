package ch.healthssi.pharmacy.data

import kotlinx.serialization.Serializable


@Serializable
data class MedicamentRefDataDTO(
    val atype: String,
    val gtin: String,
    val swmcAuthnr: String,
    val nameDe: String,
    val nameFr: String,
    val atc: String? = null,
    val authHolderName: String,
    val authHolderGln: String? = null,
    val date: String
)

@Serializable
data class PrescriptionData(
    val stateId: String,
    val issuanceDate: String?,
    val expirationDate: String?,
    val doctor: String?,
    val doctorDid: String?,
    val patientFirstName: String?,
    val patientLastName: String?,
    val patientBirthDate: String?,
    val patientDid: String?,
    val prescriptionId: String?,
    val medicationId: String?,
    val medicationRefData: MedicamentRefDataDTO?,
    val verificationSuccess: Boolean = false
)

