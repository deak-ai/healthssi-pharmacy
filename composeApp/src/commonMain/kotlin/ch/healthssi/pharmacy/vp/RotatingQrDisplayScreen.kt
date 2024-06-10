package ch.healthssi.pharmacy.vp

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp

@Composable
fun RotatingQrDisplayScreen(vpViewModel: RequestPresentationViewModel,
                            prescriptionViewModel: PrescriptionViewModel) {

    val qrCodeImage by vpViewModel.qrCodeImage.collectAsState()
    val webSocketMessage by prescriptionViewModel.webSocketMessage.collectAsState()

    var showBack by remember { mutableStateOf(false) }
    val transition = updateTransition(targetState = showBack, label = "flipTransition")

    val rotationYAnim by transition.animateFloat(
        transitionSpec = { tween(durationMillis = 600) },
        label = "rotationYAnimation"
    ) { isBack -> if (isBack) 180f else 0f }

    val alphaAnim by transition.animateFloat(
        transitionSpec = { tween(durationMillis = 600) },
        label = "alphaAnimation"
    ) { isBack -> if (isBack) 1f else 0f }

    LaunchedEffect(webSocketMessage) {
        if (webSocketMessage.isNotEmpty()) {
            showBack = true
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        if (qrCodeImage != null) {
            Box(
                modifier = Modifier
                    .size(600.dp)
                    .graphicsLayer {
                        rotationY = rotationYAnim
                        cameraDistance = 8 * density
                    }
            ) {
                if (rotationYAnim <= 90f || rotationYAnim >= 270f) {
                    Image(
                        bitmap = qrCodeImage!!,
                        contentDescription = "QR Code",
                        modifier = Modifier
                            .fillMaxSize()
                            .alpha(1f - alphaAnim)
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .graphicsLayer {
                                rotationY = 180f
                            }
                            .background(MaterialTheme.colorScheme.surface)
                            .alpha(alphaAnim),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(webSocketMessage, style = MaterialTheme.typography.headlineSmall)
                    }
                }
            }
        } else {
            Text("<No QR>>")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { vpViewModel.handleEvent(VPEvent.Reset) },
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            Text("Back", modifier = Modifier.padding(8.dp))
        }
    }
}
