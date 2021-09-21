package com.portifolio.todoapp.fragments.add

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.portifolio.todoapp.R
import com.portifolio.todoapp.data.model.TodoEntity
import com.portifolio.todoapp.databinding.FragmentAddBinding
import com.portifolio.todoapp.viewmodel.SharedViewModel
import com.portifolio.todoapp.viewmodel.TodoViewModel

class AddFragment : Fragment() {


    private var _binding : FragmentAddBinding? = null
    private val binding get() = _binding!!
    private val todoViewModel : TodoViewModel by viewModels()
    private val sharedViewModel : SharedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAddBinding.inflate(layoutInflater)

        setHasOptionsMenu(true)

        binding.prioritiesSpinner.onItemSelectedListener = sharedViewModel.listener

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_fragment_menu, menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.menu_add){
            insertDataToDatabase()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun insertDataToDatabase() {
       val title = binding.textTitleEditText.text.toString()
       val priority = binding.prioritiesSpinner.selectedItem.toString()
       val description = binding.descriptionEditText.text.toString()

       val validation = sharedViewModel.verifyDataFromTodo(title, description)

        if (validation){
            val newData = TodoEntity(0, title, sharedViewModel.parseStringToPriority(priority), description)

            todoViewModel.insertTodo(newData)

            Toast.makeText(requireContext(), "Todo Added", Toast.LENGTH_SHORT).show()

            findNavController().navigate(AddFragmentDirections.actionAddFragmentToListFragment())

        } else {

            Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}