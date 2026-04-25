package com.example.lifeharmonyapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Указываем, какие таблицы (entities) входят в базу и версию базы.
@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    // Метод, через который мы будем получать доступ к командам (DAO)
    abstract fun userDao(): UserDao

    companion object {
        // @Volatile гарантирует, что значение переменной INSTANCE всегда актуально для всех потоков
        @Volatile
        private var INSTANCE: AppDatabase? = null

        // Функция получения базы. Если она уже создана — вернет её, если нет — создаст.
        fun getDatabase(context: Context): AppDatabase {
            // synchronized защищает от одновременного создания базы из двух разных мест
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "life_harmony_db" // Имя файла самой базы данных
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}