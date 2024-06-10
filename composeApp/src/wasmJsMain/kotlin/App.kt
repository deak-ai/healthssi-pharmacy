import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import ch.healthssi.pharmacy.vp.RequestPresentationScreen
import ch.healthssi.pharmacy.di.appModule
import org.koin.compose.KoinApplication


@Composable
fun App() {
    KoinApplication(application = { modules(appModule) }) {
        MaterialTheme {

            RequestPresentationScreen()
        }
    }
}





// var showContent by remember { mutableStateOf(false) }
//
//            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
//                Button(onClick = { showContent = !showContent }) {
//                    Text("Click me!")
//                }
//                AnimatedVisibility(showContent) {
//                    val greeting = remember { Greeting().greet() }
//                    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
//                        Image(painterResource(Res.drawable.compose_multiplatform), null)
//                        Text("Compose: $greeting")
//                    }
//                }
//            }