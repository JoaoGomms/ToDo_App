package com.portifolio.todoapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.portifolio.todoapp.R
import com.portifolio.todoapp.data.model.Priority
import com.portifolio.todoapp.data.model.TodoEntity
import com.portifolio.todoapp.databinding.ListItemBinding
import com.portifolio.todoapp.fragments.list.ListFragmentDirections

class TodoAdapter : RecyclerView.Adapter<TodoAdapter.ViewHolder>() {

    var todoList: List<TodoEntity> = emptyList()

    inner class ViewHolder(val binding: ListItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ListItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentItem = todoList[position]

        holder.binding.titleTextView.text = currentItem.title
        holder.binding.descriptionTextView.text = currentItem.description


        when(currentItem.priority) {
            Priority.HIGH -> holder.binding.proprityIndicator.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.red))
            Priority.MEDIUM -> holder.binding.proprityIndicator.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.yellow))
            Priority.LOW -> holder.binding.proprityIndicator.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.green))
        }

        holder.binding.listItem.setOnClickListener{
            val action = ListFragmentDirections.actionListFragmentToUpdateFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }

    }

    override fun getItemCount() = todoList.size

    fun setData(newList: List<TodoEntity>){
        this.todoList = newList
        notifyDataSetChanged()
    }


}