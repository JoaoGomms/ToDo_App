package com.portifolio.todoapp.fragments.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.portifolio.todoapp.R
import com.portifolio.todoapp.databinding.FragmentListBinding


class ListFragment : Fragment() {

    private var _binding : FragmentListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentListBinding.inflate(layoutInflater)

        binding.floatingActionButton.setOnClickListener{

            val action = ListFragmentDirections.actionListFragmentToAddFragment()

            findNavController().navigate(action)

        }

        return binding.root
    }




    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}