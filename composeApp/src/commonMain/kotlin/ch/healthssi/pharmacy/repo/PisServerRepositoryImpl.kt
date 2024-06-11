package ch.healthssi.pharmacy.repo

import ch.healthssi.pharmacy.data.MedicamentRefDataDTO
import ch.healthssi.pharmacy.data.RequestPrescription
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

class PisServerRepositoryImpl(
    private val httpClient: HttpClient,
    private val baseUrl: String
):PisServerRepository {

    override suspend fun findMedicamentRefDataBySubstring(substring: String): Result<List<MedicamentRefDataDTO>> {
        TODO("Not yet implemented")
    }

    override suspend fun findMedicamentRefDataByGTIN(gtin: String): Result<MedicamentRefDataDTO> {
        return try {
            val response: HttpResponse = httpClient.get(
                "$baseUrl/medications/refdata/$gtin") {
                expectSuccess = true
            }
            Result.success(response.body<MedicamentRefDataDTO>())
        } catch (t: Throwable) {
            Result.failure(t)
        }
    }

    override suspend fun createPrescriptionPresentationRequest(requestPrescription: RequestPrescription): Result<Url> {
        return try {
            val response: HttpResponse = httpClient.post("$baseUrl/vp/request") {
                contentType(ContentType.Application.Json)
                setBody(requestPrescription)
                expectSuccess = true
            }
            Result.success(Url(response.body<String>()))
        } catch (t: Throwable) {
            Result.failure(t)
        }
    }

    override suspend fun generateQrCode(qrCodeString: String): Result<ByteArray> {
        return try {
            val response: HttpResponse = httpClient.post("$baseUrl/utils/qrcode") {
                contentType(ContentType.Text.Plain)
                setBody(qrCodeString)
                expectSuccess = true
            }
            Result.success(response.body<ByteArray>())
        } catch (t: Throwable) {
            Result.failure(t)
        }
    }


}