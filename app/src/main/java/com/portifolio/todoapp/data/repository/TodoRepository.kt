package com.portifolio.todoapp.data.repository

import androidx.lifecycle.LiveData
import com.portifolio.todoapp.data.dao.TodoDao
import com.portifolio.todoapp.data.model.TodoEntity

class TodoRepository(private val dao : TodoDao) {

    val todos = dao.getAllTodos()

    suspend fun insert(todo: TodoEntity) {
        dao.insertTodo(todo)
    }

    suspend fun delete(todo: TodoEntity) {
        dao.deleteTodo(todo)
    }

    suspend fun update(todo: TodoEntity) {
        dao.updateTodo(todo)
    }

    suspend fun deleteAll(){
        dao.deleteAll()
    }

    fun searchDatabase(searchQuery: String): LiveData<List<TodoEntity>>{
        return dao.searchDatabase(searchQuery)
    }


}