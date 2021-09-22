package com.portifolio.todoapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.portifolio.todoapp.data.database.AppDatabase
import com.portifolio.todoapp.data.model.TodoEntity
import com.portifolio.todoapp.data.repository.TodoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TodoViewModel(application: Application) : AndroidViewModel(application) {

    private val todos: LiveData<List<TodoEntity>>
    private val sortByHighPriority: LiveData<List<TodoEntity>>
    private val sortByLowPriority: LiveData<List<TodoEntity>>

    private val repository: TodoRepository

    fun getTodos() = todos
    fun getSortByHighPriority() = sortByHighPriority
    fun getSortByLowPriority() = sortByLowPriority

    init {
        val todoDao = AppDatabase.getDatabaseInstance(application).todoDao()
        repository = TodoRepository(todoDao)
        todos = repository.todos
        sortByHighPriority = repository.sortByHighPriority
        sortByLowPriority = repository.sortByLowPriority
    }

    fun deleteAll(){

        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
        }

    }

    fun insertTodo(todo: TodoEntity){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(todo)
        }
    }

    fun deleteTodo(todo: TodoEntity){
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(todo)
        }
    }

    fun updateTodo(todo: TodoEntity){
        viewModelScope.launch(Dispatchers.IO) {
            repository.update(todo)
        }
    }

    fun searchDatabase(searchQuery: String): LiveData<List<TodoEntity>>{
        return repository.searchDatabase(searchQuery)
    }


}