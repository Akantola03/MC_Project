package com.example.navigation


import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        NotificationHandler.CreateNotificationChannel(this)

        val db = DatabaseProvider.getDatabase(this)
        val factory = ViewModelFactory(db.messageDao())
        val viewModel = ViewModelProvider(this, factory)[ChatViewModel::class.java]


        setContent {
            NavigationTheme {
                MyNavHost(viewModel)
            }
        }
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
object MainScreen
@Serializable
object ProfilePage


// Handles the navigation between MainPage and Profile
@Composable
fun MyNavHost(viewModel: ChatViewModel, modifier: Modifier= Modifier,
              navController: NavHostController = rememberNavController()) {

    NavHost(modifier=modifier,
        navController = navController,
        startDestination = MainScreen) {

        composable<MainScreen> {
            Conversation(
                viewModel,
                onNavigateToProfilePage = { navController.navigate(route=ProfilePage) })
        }

        composable<ProfilePage> {
            ProfileScreen(onNavigateToMainScreen = { navController.popBackStack() }, viewModel)
        }
    }
}
