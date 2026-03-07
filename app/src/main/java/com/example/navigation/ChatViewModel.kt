package com.example.navigation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import android.net.Uri
import kotlinx.coroutines.delay

class ChatViewModel(
    private val dao: MessageDao,
    private val context: Context
): ViewModel() {

    val message = dao.getAllMessages()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    fun onEvent(event: MessageEvent) {
        when (event) {
            is MessageEvent.SendMessage -> {
                sendMessage(event.content)
            }
            is MessageEvent.DeleteMessage -> {
                viewModelScope.launch {
                    dao.deleteMessage(event.message)
                }
            }
            MessageEvent.ClearChat -> {
                clearChat()
            }
        }
    }

    private fun sendMessage(text: String) {
        if (text.isBlank()) return

        viewModelScope.launch {
            // Insert user message
            dao.insertMessage(
                MessageEntity(content = text,
                    isFromUser = true
                )
            )

            // Fake delay to ChatBOT's reply.
            delay(3500)

            val reply = generateReply(text)

            dao.insertMessage(
                MessageEntity(
                    content = reply,
                    isFromUser = false
                )
            )

            // Send notification if app is running in background.
            if (!AppState.isForeground) {
                NotificationHandler.SendBotMessageNotification(
                    context,
                    "ChatBot",
                    message = reply)
            }
        }
    }
    // Could be used to clear the whole chat, but is currently not used.
    private  fun clearChat() {
        viewModelScope.launch {
            dao.clearAll()
        }
    }

    // Pre coded replies by the "ChatBot".
    private fun generateReply(input: String): String {
        return when {
            input.contains("hello", true) -> "Hi there!"
            input.contains("how are you", true) -> "I'm good."
            input.contains("bye", true) -> "See you soon!"
            else -> "I have nothing more to say."
        }
    }
}