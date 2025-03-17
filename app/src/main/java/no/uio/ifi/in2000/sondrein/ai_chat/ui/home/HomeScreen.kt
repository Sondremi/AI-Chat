package no.uio.ifi.in2000.sondrein.ai_chat.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.input.key.*
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.jeziellago.compose.markdowntext.MarkdownText
import kotlinx.coroutines.launch
import no.uio.ifi.in2000.sondrein.ai_chat.ui.loader.Loader

@Composable
fun HomeScreen() {
    val homeViewModel: HomeViewModel = viewModel()
    val uiState by homeViewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    var input by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "AI Chat",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = input,
            onValueChange = { input = it },
            label = { Text("Ask a question") },
            modifier = Modifier
                .fillMaxWidth()
                .onKeyEvent { keyEvent ->
                    if (keyEvent.type == KeyEventType.KeyUp && keyEvent.key == Key.Enter) {
                        keyboardController?.hide()
                        coroutineScope.launch {
                            homeViewModel.askQuestion(input)
                        }
                        true
                    } else {
                        false
                    }
                },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                    coroutineScope.launch {
                        homeViewModel.askQuestion(input)
                    }
                }
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                keyboardController?.hide()
                coroutineScope.launch {
                    homeViewModel.askQuestion(input)
                }
            },
            modifier = Modifier.fillMaxWidth(0.6f)
        ) {
            Text("Submit", fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (uiState.isLoading) {
            Loader()
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(8.dp)
                    .verticalScroll(scrollState)
            ) {
                MarkdownText(
                    markdown = uiState.response,
                    modifier = Modifier.padding(8.dp),
                    style = MaterialTheme.typography.bodyMedium
                        .copy(fontSize = 16.sp)
                )
            }
        }
    }
}

