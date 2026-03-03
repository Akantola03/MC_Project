package com.example.navigation

sealed interface MessageEvent {

    data class SendMessage(val content: String): MessageEvent

    object ClearChat: MessageEvent
}