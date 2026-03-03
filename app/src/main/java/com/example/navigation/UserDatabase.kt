package com.example.navigation

import androidx.room.Database
import androidx.room.RoomDatabase

// Defines the database
@Database(entities = [
    UserEntity::class],
    version = 1
)

abstract class UserDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
}