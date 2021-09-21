package com.portifolio.todoapp.fragments.update

import android.app.AlertDialog
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
            R.id.menu_save -> updateItem()
            R.id.menu_delete -> confirmItemRemoval()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun confirmItemRemoval() {
        val builder = AlertDialog.Builder(requireActivity())

        builder.setPositiveButton("Yes") { _, _ ->
            todoViewModel.deleteTodo(args.currentTodo)
            Toast.makeText(requireContext(), "Successfully remove ${args.currentTodo.title}!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(action)
        }

        builder.setNegativeButton("No") { _, _ -> }

        builder.setTitle("Delete ${args.currentTodo.title}?")
        builder.setMessage("Are you sure you want to remove ${args.currentTodo.title}? This action can't be undone")
        builder.create().show()
    }

    private fun updateItem(){

        val title = binding.textTitleEditText.text.toString()
        val description = binding.descriptionEditText.text.toString()
        val priority = binding.prioritiesSpinner.selectedItem.toString()

        val validation = sharedViewModel.verifyDataFromTodo(title, description)

        if (validation){

            val updateItem = TodoEntity(
                args.currentTodo.id,
                title,
                sharedViewModel.parseStringToPriority(priority),
                description
            )

            todoViewModel.updateTodo(updateItem)

            Toast.makeText(requireContext(), "Successfully updated!", Toast.LENGTH_SHORT).show()

            findNavController().navigate(action)

        } else {
            Toast.makeText(requireContext(), "Must fill all fields", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}