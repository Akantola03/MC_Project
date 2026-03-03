package com.example.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

/**
 * SampleData for Jetpack Compose Tutorial 
 */

object SampleData {
    @Composable
    fun conversationSample(viewModel: MyViewModel): List<Message> {
        val user by viewModel.user.collectAsState()

        // Sample conversation data
        return listOf(
            Message(
                user?.username ?: "None",
                "Hello There!"
            ),
            Message(
                user?.username ?: "None",
                """List of Android versions:
            |Android KitKat (API 19)
            |Android Lollipop (API 21)
            |Android Marshmallow (API 23)
            |Android Nougat (API 24)
            |Android Oreo (API 26)
            |Android Pie (API 28)
            |Android 10 (API 29)
            |Android 11 (API 30)
            |Android 12 (API 31)""".trim()
            ),
            Message(
                user?.username ?: "None",
                """I think Kotlin is my favorite programming language.
            |It's so much fun!""".trim()
            ),
            Message(
                user?.username ?: "None",
                "Searching for alternatives to XML layouts..."
            ),
            Message(
                user?.username ?: "None",
                """Hey, take a look at Jetpack Compose, it's great!
            |It's the Android's modern toolkit for building native UI.
            |It simplifies and accelerates UI development on Android.
            |Less code, powerful tools, and intuitive Kotlin APIs :)""".trim()
            ),
            Message(
                user?.username ?: "None",
                "It's available from API 21+ :)"
            ),
            Message(
                user?.username ?: "None",
                "Writing Kotlin for UI seems so natural, Compose where have you been all my life?"
            ),
            Message(
                user?.username ?: "None",
                "Android Studio next version's name is Arctic Fox"
            ),
            Message(
                user?.username ?: "None",
                "Android Studio Arctic Fox tooling for Compose is top notch ^_^"
            ),
            Message(
                user?.username ?: "None",
                "I didn't know you can now run the emulator directly from Android Studio"
            ),
            Message(
                user?.username ?: "None",
                "Compose Previews are great to check quickly how a composable layout looks like"
            ),
            Message(
                user?.username ?: "None",
                "Previews are also interactive after enabling the experimental setting"
            ),
            Message(
                user?.username ?: "None",
                "Have you tried writing build.gradle with KTS?"
            ),
            Message(
                user?.username ?: "None",
                "Have you tried writing build.gradle with KTS?"
            ),
            Message(
                user?.username ?: "None",
                "Have you tried writing build.gradle with KTS?"
            ),
            Message(
                user?.username ?: "None",
                "Have you tried writing build.gradle with KTS?"
            ),
            Message(
                user?.username ?: "None",
                "Have you tried writing build.gradle with KTS?"
            ),
        )
    }
}
