package com.portifolio.todoapp.fragments.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.portifolio.todoapp.data.model.TodoEntity
import com.portifolio.todoapp.databinding.ListItemBinding

class TodoAdapter : RecyclerView.Adapter<TodoAdapter.ViewHolder>() {

    var todoList: List<TodoEntity> = emptyList()

    inner class ViewHolder(val binding: ListItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(todoData: TodoEntity){
            binding.toDoData = todoData
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ListItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentItem = todoList[position]
        holder.bind(currentItem)

    }

    override fun getItemCount() = todoList.size

    fun setData(newList: List<TodoEntity>){

        val todoDiffUtil = ToDoDiffUtil(todoList, newList)
        val todoDiffResult = DiffUtil.calculateDiff(todoDiffUtil)
        this.todoList = newList
        todoDiffResult.dispatchUpdatesTo(this)

    }


}