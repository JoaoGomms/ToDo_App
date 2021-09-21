package com.portifolio.todoapp.fragments

import android.view.View
import android.widget.Spinner
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.portifolio.todoapp.R
import com.portifolio.todoapp.data.model.Priority
import com.portifolio.todoapp.data.model.TodoEntity
import com.portifolio.todoapp.fragments.list.ListFragmentDirections

class BindingAdapters {

    companion object {
        @BindingAdapter("android:navigateToAddFragment")
        @JvmStatic
        fun navigateToAddFragment(view: FloatingActionButton, navigate: Boolean){
            view.setOnClickListener{
                if (navigate){
                    val action = ListFragmentDirections.actionListFragmentToAddFragment()
                    view.findNavController().navigate(action)
                }
            }
        }

        @BindingAdapter("android:sendDataToUpdateFragmentr")
        @JvmStatic
        fun sendDataToUpdateFragmentr(view: ConstraintLayout, currentItem: TodoEntity){
            view.setOnClickListener{

                    val action = ListFragmentDirections.actionListFragmentToUpdateFragment(currentItem)
                    view.findNavController().navigate(action)

            }
        }

        @BindingAdapter("android:emptyDatabase")
        @JvmStatic
        fun verifyEmptyDatabase(view: View, emptyDatabase: Boolean){
            when(emptyDatabase){
                true -> view.visibility = View.VISIBLE
                false -> view.visibility = View.INVISIBLE
            }
        }

        @BindingAdapter("android:parsePriorityToInt")
        @JvmStatic
        fun parsePriorityToInt(view: Spinner, priority: Priority){

            when(priority){
                    Priority.HIGH -> {view.setSelection(0)}
                    Priority.MEDIUM -> {view.setSelection(1)}
                    Priority.LOW -> {view.setSelection(2)}

                }

        }

        @BindingAdapter("android:parsePriorityToColor")
        @JvmStatic
        fun parsePriorityToColor(cardView: CardView, priority: Priority){
            when(priority) {
                Priority.HIGH -> cardView.setCardBackgroundColor(ContextCompat.getColor(cardView.context, R.color.red))
                Priority.MEDIUM -> cardView.setCardBackgroundColor(ContextCompat.getColor(cardView.context, R.color.yellow))
                Priority.LOW -> cardView.setCardBackgroundColor(ContextCompat.getColor(cardView.context, R.color.green))
            }
        }



    }

}