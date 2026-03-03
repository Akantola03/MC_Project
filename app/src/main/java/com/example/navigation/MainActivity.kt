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
        val factory = ViewModelFactory(db.userDao())
        val viewModel = ViewModelProvider(this, factory)[MyViewModel::class.java]


        setContent {
            NavigationTheme {
                MyNavHost(viewModel)
            }
        }
    }
}

// create an instance of database
object DatabaseProvider {
    fun getDatabase(context: Context): UserDatabase =
        Room.databaseBuilder(
            context,
            UserDatabase::class.java,
            "user_db"
        ).build()
}


@Serializable
object MainScreen
@Serializable
object ProfilePage


// Handles the navigation between MainPage and Profile
@Composable
fun MyNavHost(viewModel: MyViewModel, modifier: Modifier= Modifier,
              navController: NavHostController = rememberNavController()) {


    NavHost(modifier=modifier,
        navController = navController,
        startDestination = MainScreen) {

        composable<MainScreen> {
            Conversation(viewModel,SampleData.conversationSample(viewModel),
                onNavigateToProfilePage = { navController.navigate(route=ProfilePage) })
        }

        composable<ProfilePage> {
            ProfileScreen(onNavigateToMainScreen = { navController.popBackStack() }, viewModel)
        }
    }
}
