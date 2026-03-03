package com.example.navigation


import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.ui.res.painterResource

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.launch


@Composable
fun ProfileScreen(onNavigateToMainScreen: () -> Unit, viewModel: ChatViewModel) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val message by viewModel.message.collectAsState()
    // Controls the button location
    Box(modifier = Modifier.fillMaxSize()){
        Button(onClick = onNavigateToMainScreen,
            modifier = Modifier
                .align(Alignment.TopStart)) {
            Text(text = "Messages")
        }
    }

    Row(modifier = Modifier.padding(top = 60.dp, start = 8.dp, end = 8.dp)) {
        Image(
            // Here we choose the image and edit its properties
            painter = painterResource(R.drawable.cv_kuva1),
            contentDescription = null,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape)
        )
        Column {
            Text(
                text = "Akris",
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text("This is my profile")

            Spacer(modifier = Modifier.height(30.dp))

            //InputNewName(viewModel)

            //ShowImage(viewModel)

            // Permission launcher
            val permissionLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestPermission()
            ) { isGranted ->
                if(isGranted) {
                    NotificationHandler.SendNotification(context)
                } else {
                    Toast.makeText(context, "Denied", Toast.LENGTH_SHORT).show()
                }
            }
            Button(onClick = {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    permissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
                } else {
                    NotificationHandler.SendNotification(context)
                }
            }) {Text("Allow Notifications")}
        }
    }
}
/*
// Shows the text field for new profile name
@Composable
fun InputNewName(viewModel: ChatViewModel) {
    val user by viewModel.user.collectAsState()
    var myInput by remember { mutableStateOf(user?.username ?: "") }

    TextField(
        value = myInput,
        onValueChange = {myInput = it },
        label = { Text("Profile name")}
    )
    Button(onClick = { viewModel.saveUsername(myInput) }) {
        Text(text = "Save")
    }
}

@Composable
fun ShowImage(viewModel: ChatViewModel) {
    val context = LocalContext.current
    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        uri?.let { viewModel.saveImageFromGallery(it, context)}
    }

    Spacer(modifier = Modifier.height(40.dp))
    Button(onClick = {
        pickImageLauncher.launch(
            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
        )
    }, modifier = Modifier) {
        Text(text = "New photo")
    }
}

*/