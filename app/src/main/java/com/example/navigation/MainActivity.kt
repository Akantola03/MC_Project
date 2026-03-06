package com.example.navigation


import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.NotificationCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.navigation.ui.theme.NavigationTheme

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import androidx.compose.foundation.Image
import androidx.compose.runtime.getValue


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        NotificationHandler.CreateNotificationChannel(this)

        val db = DatabaseProvider.getDatabase(this)
        val factory = ViewModelFactory(db.messageDao(), applicationContext)
        val viewModel = ViewModelProvider(this, factory)[ChatViewModel::class.java]

        // Check if notification requested chat screen
        val openChat = intent?.getBooleanExtra("open_chat", false) ?: false

        setContent {

            NavigationTheme {
                MyNavHost(
                    viewModel,
                    startInChat = openChat
                )
            }
        }
    }

    // Used to figure out if the app is foreground or not
    override fun onResume() {
        super.onResume()
        AppState.isForeground = true
    }

    override fun onPause() {
        super.onPause()
        AppState.isForeground = false
    }

}

// create an instance of database
object DatabaseProvider {
    fun getDatabase(context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "chat_db"
        ).build()
}

@Serializable
object ConversationsScreen
@Serializable
object ChatScreen
@Serializable
object ProfilePage


// Handles the navigation between ConversationsScreen, ChatScreen and ProfilePage
@Composable
fun MyNavHost(
    viewModel: ChatViewModel,
    startInChat: Boolean,
    modifier: Modifier= Modifier,
    navController: NavHostController = rememberNavController()
) {

    val startDestination =
        if (startInChat) ChatScreen else ConversationsScreen

    NavHost(modifier=modifier,
        navController = navController,
        startDestination = startDestination
    ) {

        composable<ConversationsScreen> {
            ConversationsScreen(
                onOpenChat = {navController.navigate(route=ChatScreen)},
                onNavigateToProfilePage = { navController.navigate(route=ProfilePage) }
            )
        }

        composable<ChatScreen> {
            Conversation(
                viewModel,
                onNavigateToProfilePage = { navController.navigate(route=ProfilePage) })
        }

        composable<ProfilePage> {
            ProfileScreen(onNavigateToMainScreen = { navController.popBackStack() }, viewModel)
        }
    }
}

