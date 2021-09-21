package com.portifolio.todoapp.fragments.update

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.portifolio.todoapp.R
import com.portifolio.todoapp.data.model.TodoEntity
import com.portifolio.todoapp.databinding.FragmentUpdateBinding
import com.portifolio.todoapp.viewmodel.SharedViewModel
import com.portifolio.todoapp.viewmodel.TodoViewModel

class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()
    private val sharedViewModel: SharedViewModel by viewModels()
    private val todoViewModel: TodoViewModel by viewModels()
    private val action = UpdateFragmentDirections.actionUpdateFragmentToListFragment()

    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentUpdateBinding.inflate(layoutInflater)

        bindViews()

        setHasOptionsMenu(true)

        return binding.root
    }

    private fun bindViews() {
        binding.textTitleEditText.setText(args.currentTodo.title)
        binding.descriptionEditText.setText(args.currentTodo.description)
        binding.prioritiesSpinner.setSelection(sharedViewModel.parsePriorityToInt(args.currentTodo.priority))
        binding.prioritiesSpinner.onItemSelectedListener = sharedViewModel.listener
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_fragment_menu, menu)
    }
}