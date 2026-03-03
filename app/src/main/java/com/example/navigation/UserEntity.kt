package com.example.navigation

import androidx.room.Entity
import androidx.room.PrimaryKey

// Creates Room Entity
@Entity(tableName = "user")
data class UserEntity (
    @PrimaryKey val id: Int = 0,
    val username: String?,
    val imageUri: String? = null
)