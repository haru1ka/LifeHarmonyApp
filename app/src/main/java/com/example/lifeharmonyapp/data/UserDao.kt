package com.example.lifeharmonyapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {

    // 1. РЕГИСТРАЦИЯ: Добавляет нового пользователя.
    // Возвращает ID новой строки (Long) или -1, если почта уже есть в базе.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun registerUser(user: User): Long

    // 2. ВХОД / ПРОВЕРКА: Ищет пользователя по почте.
    // Используется в LoginFragment для получения данных юзера (включая пароль и ID).
    @Query("SELECT * FROM users_table WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): User?

    // 3. АВТОРИЗАЦИЯ (Альтернатива): Поиск сразу по паре почта+пароль.
    @Query("SELECT * FROM users_table WHERE email = :email AND password = :password LIMIT 1")
    suspend fun loginUser(email: String, password: String): List<User>

    // 4. ПОДТВЕРЖДЕНИЕ: Меняет статус верификации после ввода кода из письма.
    @Query("UPDATE users_table SET isVerified = 1 WHERE email = :email")
    suspend fun verifyUser(email: String)

    // 5. ВОССТАНОВЛЕНИЕ: Установка нового пароля (экран Forgot Password).
    @Query("UPDATE users_table SET password = :newPassword WHERE email = :email")
    suspend fun updatePassword(email: String, newPassword: String)

    // 6. ПРОФИЛЬ: Загрузка данных конкретного человека по его внутреннему ID.
    // Именно этот метод мы используем, чтобы показать "Имя" и "Email" на главном экране профиля.
    @Query("SELECT * FROM users_table WHERE id = :userId")
    suspend fun getUserById(userId: Int): User?

    // --- НОВОЕ ДЛЯ РЕДАКТИРОВАНИЯ ПРОФИЛЯ ---

    // 7. ОБНОВЛЕНИЕ: Сохраняет измененные данные пользователя (имя, почту и т.д.).
    // Room сам найдет нужного юзера в таблице, сравнив ID из объекта 'user'.
    @Update
    suspend fun updateUser(user: User)

    // 8. ПРОВЕРКА УНИКАЛЬНОСТИ: Нужна при смене почты.
    // Проверяет, не занят ли новый email КЕМ-ТО ДРУГИМ (чтобы юзер случайно не "занял" чужую почту).
    @Query("SELECT * FROM users_table WHERE email = :email AND id != :currentUserId LIMIT 1")
    suspend fun isEmailTakenByOther(email: String, currentUserId: Int): User?
}