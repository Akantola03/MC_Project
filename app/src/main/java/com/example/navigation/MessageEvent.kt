package com.example.navigation

sealed interface MessageEvent {
    data class SendMessage(val content: String): MessageEvent

    data class DeleteMessage(val message: MessageEntity): MessageEvent
    object ClearChat: MessageEvent
}