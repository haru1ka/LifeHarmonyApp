package com.example.lifeharmonyapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users_table") // Создает таблицу в БД
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,               // Уникальный ID, растет сам

    val name: String,              // Имя из etName
    val email: String,             // Почта из etEmail
    val password: String,          // Пароль из etPassword
    val isVerified: Boolean = false // Прошел ли проверку (заглушка)
)