package com.example.navigation

// Saves user actions
sealed interface UserEvent {
    object SaveUser: UserEvent
    data class SetProfileName(val profileName: String): UserEvent

    object ShowDialog: UserEvent
    object HideDialog: UserEvent

}