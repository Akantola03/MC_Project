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
import coil.compose.rememberAsyncImagePainter

data class Message(val author: String, val body: String)


// Shows the message with users image, name and text.
@Composable
fun MessageCard(viewModel: MyViewModel,msg: Message){
    val user by viewModel.user.collectAsState()

    // Padding around the message
    Row(modifier = Modifier.padding(all = 8.dp)) {
        val painter = if (user?.imageUri != null){
            rememberAsyncImagePainter(Uri.parse(user!!.imageUri))
        } else {
            painterResource(R.drawable.ic_launcher_foreground)
        }

        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape)
        )
        // Adds horizontal space between image and columns
        Spacer(modifier = Modifier.width(8.dp))
        // Toggles the isExpanded variable when we click on this Column
        Column {
            Text(
                text = user?.username ?: "None",
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleLarge)

            // Vertical space between author and message
            Spacer(modifier = Modifier.width(4.dp))

            ExpandMessage(msg.body)
        }
    }
}

// Handles message expansion
@Composable
fun ExpandMessage(text: String) {
    var isExpanded by remember { mutableStateOf(false) }
    // Toggles the isExpanded variable when we click on this Column
    Column(modifier = Modifier.clickable { isExpanded = !isExpanded }) {
        Surface(shape = MaterialTheme.shapes.medium, shadowElevation = 1.dp) {
            Text(
                text = text,
                modifier = Modifier.padding(all = 4.dp),
                maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                style = MaterialTheme.typography.bodyMedium
            )
        }

    }
}

// Shows the conversation stored in SampleData using MessageCard
@Composable
fun Conversation(viewModel: MyViewModel, messages: List<Message>,  onNavigateToProfilePage: () -> Unit){
    // Controls the buttons location
    Box(modifier = Modifier.fillMaxSize()){
        Button(onClick = onNavigateToProfilePage,
            modifier = Modifier
                .align(Alignment.TopEnd)) {
            Text("Profile")
        }
    }

    Column {
        Spacer(modifier = Modifier.height(50.dp))
        LazyColumn {
            items(messages) { message ->
                MessageCard(viewModel,message)
            }
        }
    }
}
