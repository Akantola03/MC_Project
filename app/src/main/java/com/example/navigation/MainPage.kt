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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.isPopupLayout
import androidx.room.util.TableInfo
import coil.compose.rememberAsyncImagePainter

data class Message(val author: String, val body: String)


// Shows the message with users image, name and text.
@Composable
fun MessageCard(message: MessageEntity){
    val isUser = message.isFromUser

    // Padding around the message
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start
    ) {

        if (!isUser) {
            Image(
                painter = painterResource(R.drawable.minecraft_2024_cover_art),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape)
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
        ExpandMessage(text = message.content, isUser = isUser)
        }
}


// Handles message expansion
@Composable
fun ExpandMessage(text: String, isUser: Boolean) {
    var isExpanded by remember { mutableStateOf(false) }


    Surface(
        shape = MaterialTheme.shapes.medium,
        color = if(isUser)
            MaterialTheme.colorScheme.primary
        else
            MaterialTheme.colorScheme.surfaceVariant,
        shadowElevation = 1.dp,
        modifier = Modifier.clickable {isExpanded = !isExpanded}
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(all = 4.dp),
            maxLines = if (isExpanded) Int.MAX_VALUE else 2,
            color = if (isUser)
                MaterialTheme.colorScheme.onPrimary
            else
                MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

// Shows the conversation stored in SampleData using MessageCard
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
        // Message list
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            reverseLayout = false
        ) {
            items(messages) { message ->
                MessageCard(message)
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
