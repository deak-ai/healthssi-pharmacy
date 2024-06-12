package ch.healthssi.pharmacy.di

import ch.healthssi.pharmacy.vp.RequestPresentationViewModel
import ch.healthssi.pharmacy.repo.PisServerRepository
import ch.healthssi.pharmacy.repo.PisServerRepositoryImpl
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.cookies.*
import io.ktor.client.plugins.websocket.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val appModule = module {

    single {
        io.ktor.client.HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    encodeDefaults = true
                    ignoreUnknownKeys = true
                })
            }
            install(HttpCookies)
            install(WebSockets)
        }
    }

   //single<PisServerRepository> { PisServerRepositoryImpl(get(), "https://pis.healthwallet.li") }
    single<PisServerRepository> { PisServerRepositoryImpl(get(), "https://pis.healthwallet.li") }

    single<RequestPresentationViewModel> { RequestPresentationViewModel(get(),get()) }


}