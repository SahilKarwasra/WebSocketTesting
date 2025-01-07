package com.example.websockettesting

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.websockettesting.ui.theme.WebSocketTestingTheme
import io.socket.client.Socket
import io.socket.emitter.Emitter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WebSocketTestingTheme {

                // Set up socket connection
                SocketHandler.setSocket()
                val mSocket = SocketHandler.getSocket()
                SocketHandler.establishConnection()

                ChatScreen(
                    mSocket = mSocket,
                    onDisconnect = { SocketHandler.closeConnection() }
                )

            }
        }
    }
}

@Composable
fun ChatScreen(mSocket: Socket, onDisconnect: () -> Unit) {
    var message by remember { mutableStateOf("") }
    var receivedMessage by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    // Listen to "chat message" events
    LaunchedEffect(Unit) {
        mSocket.on("chat message", Emitter.Listener { args ->
            if (args.isNotEmpty()) {
                val newMessage = args[0] as String
                scope.launch(Dispatchers.Main) {
                    receivedMessage = newMessage
                }
            }
        })
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = message,
            onValueChange = { message = it },
            label = { Text("Enter a message") }
        )
        Button(
            onClick = {
                if (message.isNotBlank()) {
                    mSocket.emit("chat message", message)
                    message = ""
                }
            }
        ) {
            Text("Send")
        }
        if (receivedMessage.isNotBlank()) {
            Text("Received: $receivedMessage", modifier = Modifier.padding(top = 16.dp))
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            onDisconnect()
        }
    }
}
