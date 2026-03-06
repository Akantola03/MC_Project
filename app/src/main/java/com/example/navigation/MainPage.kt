package com.example.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import android.net.Uri
import android.text.Layout
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextButton
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.isPopupLayout
import androidx.room.util.TableInfo
import coil.compose.rememberAsyncImagePainter
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.ui.input.pointer.pointerInput



// Shows the message sent by user or bot
@Composable
fun MessageCard(
    message: MessageEntity,
    onDelete: (MessageEntity)-> Unit
){
    var showDialog by remember { mutableStateOf(false) }
    val isUser = message.isFromUser

    // Delete confirmation dialog
    if(showDialog) {
        AlertDialog(
            onDismissRequest = {showDialog = false},
            confirmButton = {
                TextButton(
                    onClick = {
                        onDelete(message)
                        showDialog = false
                    }
                ) {
                    Text("Delete")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {showDialog = false}
                ) {
                    Text("Cancel")
                }
            },
            title = {Text("Delete message?")},
            text = {Text("This message is lost forever")}
        )
    }


    // Padding around the message
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .pointerInput(Unit) {
                detectTapGestures(onLongPress = {showDialog = true}
                )
            },
        horizontalArrangement = if (isUser)
            Arrangement.End
        else
            Arrangement.Start
    ) {

        Surface(
            shape = RoundedCornerShape(16.dp),
            color = if(isUser)
                MaterialTheme.colorScheme.primary
            else
                MaterialTheme.colorScheme.surfaceVariant,
            shadowElevation = 2.dp
        ) {
            Text(
                text = message.content,
                modifier = Modifier.padding(
                    horizontal = 12.dp,
                    vertical = 8.dp
                ),
                color = if (isUser)
                    MaterialTheme.colorScheme.onPrimary
                else
                    MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

// Displays the messages stored in the Room Database
@Composable
fun Conversation(
    viewModel: ChatViewModel,
    onNavigateToProfilePage: () -> Unit
){
    val messages by viewModel.message.collectAsState()

    Column(modifier = Modifier.fillMaxSize())
    {
        // Profile button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Button(onClick = onNavigateToProfilePage) {
                Text("Profile")
            }
        }

        ChatHeader()

        // Message list
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            reverseLayout = false
        ) {
            items(messages, key = {it.id}) { message ->
                MessageCard(
                    message,
                    onDelete = {viewModel.onEvent(MessageEvent.DeleteMessage(it))
                    }
                )
            }
        }
        ChatBox(
            onSend = { text ->
                viewModel.onEvent(MessageEvent.SendMessage(text))
            }
        )
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
fun ChatBox(
    onSend: (String) -> Unit
) {
    var text by remember { mutableStateOf("") }

    Surface(
        tonalElevation = 4.dp,
        shadowElevation = 8.dp
    ) {
        Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = {text = it},
            modifier = Modifier.weight(1f),
            placeholder = { Text("Type something")},
            shape = RoundedCornerShape(24.dp),
            maxLines = 4
        )

        IconButton(
            onClick = { if (text.isNotBlank()) {
                onSend(text)
                text = ""
            }
            }
        ) {
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = "Send"
                )
            }
        }
    }
}

@Composable
fun ChatHeader() {
    Surface(
        tonalElevation = 3.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                painter = painterResource(R.drawable.minecraft_2024_cover_art),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .border(1.5.dp,
                        MaterialTheme.colorScheme.primary,
                        CircleShape
                    )
            )
            Spacer(modifier = Modifier.width(8.dp))

            Column {
                Text (
                    text = "ChatBOT",
                    style = MaterialTheme.typography.titleMedium
                )
                Text (
                    text = "Online",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

