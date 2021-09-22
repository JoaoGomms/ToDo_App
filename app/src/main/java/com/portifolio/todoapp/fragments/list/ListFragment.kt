package com.portifolio.todoapp.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.portifolio.todoapp.R
import com.portifolio.todoapp.data.model.TodoEntity
import com.portifolio.todoapp.fragments.list.adapter.TodoAdapter
import com.portifolio.todoapp.databinding.FragmentListBinding
import com.portifolio.todoapp.fragments.list.util.SwipeToDelete
import com.portifolio.todoapp.viewmodel.TodoViewModel
import jp.wasabeef.recyclerview.animators.FadeInUpAnimator


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

        setupRecyclerView()
        handleClicks()
        binding.lifecycleOwner = this
        todoViewModel.getTodos().observe(viewLifecycleOwner, {

                data ->
            adapter.setData(data)
            binding.emptyDatabaseValue = data.isEmpty()
        })



        setHasOptionsMenu(true)

        return binding.root
    }

    private fun setupRecyclerView(){

        val recyclerView = binding.listRecyclerView

        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(context, 1)
        recyclerView.itemAnimator = FadeInUpAnimator().apply {
            addDuration = 300
            removeDuration = 300
        }



        swipeToDelete(recyclerView)

    }


    private fun handleClicks() {

        binding.addFirstImageView.setOnClickListener {
            findNavController().navigate(toAddFragmentAction)
        }

    }

    private fun restoreDeletedData(view: View, deletedItem: TodoEntity, position: Int){
        val snackBar = Snackbar.make(
            view, "Deleted '${deletedItem.title}'", Snackbar.LENGTH_LONG
        )

        snackBar.setAction("Undo"){
            todoViewModel.insertTodo(deletedItem)
            adapter.notifyItemChanged(position)
        }

        snackBar.show()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun swipeToDelete(recyclerView: RecyclerView){
        val swipteToDeleteCallback = object : SwipeToDelete(){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.absoluteAdapterPosition
                val deletedItem = adapter.todoList[position]
                todoViewModel.deleteTodo(deletedItem)
                adapter.notifyItemRemoved(position)
                Toast.makeText(requireContext(), "Successfully removed  '${deletedItem.title}'", Toast.LENGTH_SHORT).show()

                restoreDeletedData(viewHolder.itemView, deletedItem, position)

            }
        }
        val itemTouchHelper = ItemTouchHelper(swipteToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
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