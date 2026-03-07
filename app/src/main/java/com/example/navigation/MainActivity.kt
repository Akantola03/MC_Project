package com.example.navigation

import android.content.Context

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import com.example.navigation.ui.theme.NavigationTheme

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        NotificationHandler.CreateNotificationChannel(this)

        val db = DatabaseProvider.getDatabase(this)
        val factory = ViewModelFactory(db.messageDao(), applicationContext)
        val viewModel = ViewModelProvider(this, factory)[ChatViewModel::class.java]

        // Check if notification requested chat screen
        val openChat = intent?.getBooleanExtra("open_chat", false) ?: false

        setContent {
            NavigationTheme {

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    MyNavHost(
                        viewModel,
                        startInChat = openChat
                    )
                }
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
                onNavigateToProfilePage = { navController.navigate(route=ProfilePage) },
                onNavigateToMainScreen = { navController.popBackStack() })
        }

        composable<ProfilePage> {
            ProfileScreen(onNavigateToMainScreen = { navController.popBackStack() }, viewModel)
        }
    }
}

