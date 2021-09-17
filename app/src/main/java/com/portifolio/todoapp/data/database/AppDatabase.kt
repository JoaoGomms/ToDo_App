package com.portifolio.todoapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.portifolio.todoapp.data.dao.TodoDao
import com.portifolio.todoapp.data.model.TodoEntity
import com.portifolio.todoapp.data.model.converters.PriorityStringConverter

@Database(entities = [TodoEntity::class], version = 1, exportSchema = true )
@TypeConverters(PriorityStringConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun todoDao() : TodoDao

    companion object {
        @Volatile
        private var INSTANCE : AppDatabase? = null

        fun getDatabaseInstance(context: Context) : AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context, AppDatabase::class.java, "todo-db"
                ).build()

                INSTANCE = instance

                instance

            }
        }

    }

}