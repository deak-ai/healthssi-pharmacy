package ch.healthssi.pharmacy.vp

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun QrDisplayScreen(vpViewModel: RequestPresentationViewModel) {
    //var qrCodeBitmap by remember { mutableStateOf<ImageBitmap?>(null) }
    val qrCode by vpViewModel.qrString.collectAsState()

    val qrCodeImage by vpViewModel.qrCodeImage.collectAsState()

    val webSocketMessage by vpViewModel.webSocketMessage.collectAsState()

    var showContent by remember { mutableStateOf(false) }
    showContent = webSocketMessage.isNotEmpty()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        qrCodeImage?.let { bitmap ->
            Image(bitmap = bitmap, contentDescription = "QR Code", modifier = Modifier.size(600.dp))
        } ?: Text("<No QR>>")

        Spacer(modifier = Modifier.height(16.dp))
        Text(qrCode, modifier = Modifier.align(
            Alignment.CenterHorizontally)
            .padding(8.dp))
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { vpViewModel.handleEvent(VPEvent.Reset) },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Back", modifier = Modifier.padding(8.dp))
        }
        Spacer(modifier = Modifier.height(16.dp))
        AnimatedVisibility(visible = showContent) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("WebSocket Message: $webSocketMessage")
            }
        }
    }
}



