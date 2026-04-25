
package com.example.lifeharmonyapp.data

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "users_table",
    indices = [Index(value = ["email"], unique = true)] // Это запретит дубликаты почты
)
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val name: String,
    val email: String,
    val password: String,
    // Используем Int (0 или 1), так как SQLite хранит Boolean как целые числа
    val isVerified: Int = 0
)