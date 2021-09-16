package com.portifolio.todoapp.fragments.list

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.portifolio.todoapp.R
import com.portifolio.todoapp.databinding.FragmentListBinding


class ListFragment : Fragment() {

    private var _binding : FragmentListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.list_fragment_menu, menu)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentListBinding.inflate(layoutInflater)

        handleClicks()

        setHasOptionsMenu(true)

        return binding.root
    }


    private fun handleClicks(){

        binding.floatingActionButton.setOnClickListener{
            val action = ListFragmentDirections.actionListFragmentToAddFragment()
            findNavController().navigate(action)
        }

        binding.listLayoutFragment.setOnClickListener{
            val action = ListFragmentDirections.actionListFragmentToUpdateFragment()
            findNavController().navigate(action)
        }

    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}