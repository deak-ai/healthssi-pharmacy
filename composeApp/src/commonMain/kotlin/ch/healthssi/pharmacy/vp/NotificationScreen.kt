package ch.healthssi.pharmacy.vp

import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.ktor.client.*
import io.ktor.client.engine.js.*
import io.ktor.client.plugins.websocket.*

import io.ktor.http.*
import io.ktor.websocket.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.*

@Composable
fun NotificationScreen() {
    var notifications by remember { mutableStateOf(listOf<String>()) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            val client = HttpClient(Js) {
                install(WebSockets)
            }
            client.ws(
                method = HttpMethod.Get,
                host = "localhost",
                port = 8080,
                path = "/notifications"
            ) {
                send("Hello, server!")
                incoming.consumeAsFlow().collect { frame ->
                    if (frame is Frame.Text) {
                        val receivedText = frame.readText()
                        notifications = notifications + receivedText
                    }
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Notifications:")
        Spacer(modifier = Modifier.height(8.dp))
        notifications.forEach { notification ->
            Text(notification)
        }
    }
}

