package ch.healthssi.pharmacy.vp

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun InitialScreen(
    vpViewModel: RequestPresentationViewModel
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Button(
            onClick = { vpViewModel.requestPresentation() },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Request Prescription")
        }

        Spacer(modifier = Modifier.height(16.dp))

        //Text(vpRequest, modifier = Modifier.padding(8.dp))

    }
}