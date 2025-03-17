package no.uio.ifi.in2000.sondrein.ai_chat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import no.uio.ifi.in2000.sondrein.ai_chat.ui.home.HomeScreen
import no.uio.ifi.in2000.sondrein.ai_chat.ui.theme.AIChatTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AIChatTheme {
                HomeScreen()
            }
        }
    }
}
