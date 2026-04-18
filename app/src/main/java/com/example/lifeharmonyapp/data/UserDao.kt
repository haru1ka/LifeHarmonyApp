package com.example.lifeharmonyapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun registerUser(user: User): Long

    // Возвращаем список. Если в списке 0 элементов — логин не удался.
    // Это избавляет от ошибки с Continuation в Java-коде.
    @Query("SELECT * FROM users_table WHERE email = :email AND password = :password LIMIT 1")
    suspend fun loginUser(email: String, password: String): List<User>

    @Query("UPDATE users_table SET isVerified = 1 WHERE email = :email")
    suspend fun verifyUser(email: String)
}