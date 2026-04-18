package com.example.lifeharmonyapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun registerUser(user: User): Long

    // 1. ЭТОТ МЕТОД НУЖЕН ДЛЯ LOGINFRAGMENT (поиск по почте)
    @Query("SELECT * FROM users_table WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): User?

    // Оставляем твой метод на всякий случай, если ты захочешь зайти "в одно действие"
    @Query("SELECT * FROM users_table WHERE email = :email AND password = :password LIMIT 1")
    suspend fun loginUser(email: String, password: String): List<User>

    @Query("UPDATE users_table SET isVerified = 1 WHERE email = :email")
    suspend fun verifyUser(email: String)

    // Метод для сохранения нового пароля
    @Query("UPDATE users_table SET password = :newPassword WHERE email = :email")
    suspend fun updatePassword(email: String, newPassword: String)
}