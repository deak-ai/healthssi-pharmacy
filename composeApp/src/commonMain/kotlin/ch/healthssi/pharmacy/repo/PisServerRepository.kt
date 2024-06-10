package ch.healthssi.pharmacy.repo

import ch.healthssi.pharmacy.data.MedicamentRefDataDTO
import ch.healthssi.pharmacy.data.RequestPrescription
import io.ktor.http.*
import kotlin.Result

interface  PisServerRepository {

    suspend fun findMedicamentRefDataBySubstring(substring: String): Result<List<MedicamentRefDataDTO>>

    suspend fun findMedicamentRefDataByGTIN(gtin: String): Result<MedicamentRefDataDTO>

    suspend fun createPrescriptionPresentationRequest(requestPrescription: RequestPrescription): Result<Url>

    suspend fun generateQrCode(qrCodeString: String): Result<ByteArray>

}