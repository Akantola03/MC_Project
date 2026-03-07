package com.example.navigation

import android.content.pm.PackageManager
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
import androidx.compose.ui.Alignment
import android.os.Build
import android.widget.Toast
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton

// Displays username, image, back button and "Allow Notifications" button.
@Composable
fun ProfileScreen(onNavigateToMainScreen: () -> Unit, viewModel: ChatViewModel) {
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()){
        IconButton(onClick = onNavigateToMainScreen,
            modifier = Modifier
                .align(Alignment.TopStart)) {
            Icon(imageVector = Icons.Default.ArrowBack,
                contentDescription = "back to messages")
        }
    }

    Row(modifier = Modifier.padding(top = 60.dp, start = 8.dp, end = 8.dp)) {
        Image(
            painter = painterResource(R.drawable.cv_kuva1),
            contentDescription = null,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape)
        )
        Column {
            // Profile image and name
            Text(
                text = "Akris",
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text("This is my profile")

            Spacer(modifier = Modifier.height(30.dp))


            // Permission launcher
            val permissionLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestPermission()
            ) { isGranted ->
                if(isGranted) {
                    NotificationHandler.SendEnableNotification(context)
                } else {
                    Toast.makeText(context, "Denied", Toast.LENGTH_SHORT).show()
                }
            }

            Button(onClick = {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

                    if (ContextCompat.checkSelfPermission(
                        context,
                        android.Manifest.permission.POST_NOTIFICATIONS
                        )== PackageManager.PERMISSION_GRANTED
                    ) {
                        // Permission already granted
                        NotificationHandler.SendEnableNotification(context)

                    } else {
                        // Show popup
                        permissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
                    }
                } else {
                    NotificationHandler.SendEnableNotification(context)
                }
            }) {Text("Allow Notifications")
            }
        }
    }
}