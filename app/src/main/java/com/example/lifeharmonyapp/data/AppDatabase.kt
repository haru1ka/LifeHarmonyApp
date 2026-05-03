package com.example.lifeharmonyapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// 1. УВЕЛИЧИЛИ ВЕРСИЮ: Была 1, стала 2, так как мы добавили новые поля в User.kt
@Database(entities = [User::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    // Метод, через который мы будем получать доступ к командам (DAO)
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        // Функция получения базы.
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "life_harmony_db" // Имя файла базы данных
                )
                    // 2. ДОБАВИЛИ МИГРАЦИЮ:
                    // Это позволит Room очистить старую таблицу и создать новую при запуске,
                    // чтобы не было конфликтов из-за новых колонок.
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}