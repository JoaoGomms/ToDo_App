package com.portifolio.todoapp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.portifolio.todoapp.data.model.TodoEntity

@Dao
interface TodoDao {

    @Query("SELECT * FROM TODO_TABLE")
    fun getAllTodos() : LiveData<List<TodoEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTodo(todoEntity: TodoEntity)

    @Update
    suspend fun updateTodo(todoEntity: TodoEntity)

    @Delete
    suspend fun deleteTodo(todoEntity: TodoEntity)

    @Query("DELETE FROM TODO_TABLE")
    suspend fun deleteAll()

}