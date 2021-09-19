package com.portifolio.todoapp.fragments.list

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.portifolio.todoapp.R
import com.portifolio.todoapp.adapter.TodoAdapter
import com.portifolio.todoapp.databinding.FragmentListBinding
import com.portifolio.todoapp.viewmodel.TodoViewModel


class ListFragment : Fragment() {

    private var _binding : FragmentListBinding? = null
    private val binding get() = _binding!!
    private val adapter: TodoAdapter by lazy { TodoAdapter() }
    private val todoViewModel: TodoViewModel by viewModels()

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

        todoViewModel.getTodos().observe(viewLifecycleOwner, {
                data -> adapter.setData(data)
        })

        handleClicks()

        setHasOptionsMenu(true)

        return binding.root
    }


    private fun handleClicks(){

        binding.floatingActionButton.setOnClickListener{
            val action = ListFragmentDirections.actionListFragmentToAddFragment()
            findNavController().navigate(action)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}