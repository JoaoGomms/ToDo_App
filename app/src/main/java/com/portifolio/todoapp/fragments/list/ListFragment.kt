package com.portifolio.todoapp.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.portifolio.todoapp.R
import com.portifolio.todoapp.adapter.TodoAdapter
import com.portifolio.todoapp.databinding.FragmentListBinding
import com.portifolio.todoapp.viewmodel.TodoViewModel


class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private val adapter: TodoAdapter by lazy { TodoAdapter() }
    private val todoViewModel: TodoViewModel by viewModels()
    val toAddFragmentAction = ListFragmentDirections.actionListFragmentToAddFragment()

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.list_fragment_menu, menu)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentListBinding.inflate(layoutInflater)

        binding.listRecyclerView.adapter = adapter
        binding.listRecyclerView.layoutManager = GridLayoutManager(context, 1)
        handleClicks()
        todoViewModel.getTodos().observe(viewLifecycleOwner, {

                data ->
            adapter.setData(data)
            if (data.isEmpty()) {
                binding.addFirstTextView.visibility = View.VISIBLE
                binding.addFirstImageView.visibility = View.VISIBLE
            }
        })



        setHasOptionsMenu(true)

        return binding.root
    }


    private fun handleClicks() {

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(toAddFragmentAction)
        }

        binding.addFirstImageView.setOnClickListener {
            findNavController().navigate(toAddFragmentAction)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.menu_delete_all -> confirmeDeleteAll()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun confirmeDeleteAll() {
        val builder = AlertDialog.Builder(requireContext())

        builder.setTitle("Delete all ToDos?")
        builder.setMessage("This action can't be undone")

        builder.setPositiveButton("Yes") { _, _ ->
            todoViewModel.deleteAll()
            Toast.makeText(requireContext(), "All files deleted", Toast.LENGTH_SHORT).show()

        }

        builder.setNegativeButton("No") { _, _ -> }
        builder.create().show()

    }
}