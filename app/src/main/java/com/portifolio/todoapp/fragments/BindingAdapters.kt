package com.portifolio.todoapp.fragments

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
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

        @BindingAdapter("android:emptyDatabase")
        @JvmStatic
        fun verifyEmptyDatabase(view: View, emptyDatabase: Boolean){
            when(emptyDatabase){
                true -> view.visibility = View.VISIBLE
                false -> view.visibility = View.INVISIBLE
            }
        }

    }

}