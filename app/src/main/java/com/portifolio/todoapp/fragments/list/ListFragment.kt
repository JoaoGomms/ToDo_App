package com.portifolio.todoapp.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.portifolio.todoapp.R
import com.portifolio.todoapp.data.model.TodoEntity
import com.portifolio.todoapp.fragments.list.adapter.TodoAdapter
import com.portifolio.todoapp.databinding.FragmentListBinding
import com.portifolio.todoapp.fragments.list.util.SwipeToDelete
import com.portifolio.todoapp.util.hideKeyboard
import com.portifolio.todoapp.util.observeOnce
import com.portifolio.todoapp.viewmodel.TodoViewModel
import jp.wasabeef.recyclerview.animators.FadeInUpAnimator


class ListFragment : Fragment(), SearchView.OnQueryTextListener {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private val adapter: TodoAdapter by lazy { TodoAdapter() }
    private val todoViewModel: TodoViewModel by viewModels()
    val toAddFragmentAction = ListFragmentDirections.actionListFragmentToAddFragment()

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.list_fragment_menu, menu)

        val search = menu.findItem(R.id.menu_search)
        val searchView = search.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)

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

        hideKeyboard(requireActivity())

        setHasOptionsMenu(true)

        return binding.root
    }

    private fun setupRecyclerView(){

        val recyclerView = binding.listRecyclerView

        recyclerView.adapter = adapter
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.itemAnimator = FadeInUpAnimator().apply {
            addDuration = 100
            removeDuration = 100
        }



        swipeToDelete(recyclerView)

    }


    private fun handleClicks() {

        binding.addFirstImageView.setOnClickListener {
            findNavController().navigate(toAddFragmentAction)
        }

    }

    private fun restoreDeletedData(view: View, deletedItem: TodoEntity){
        val snackBar = Snackbar.make(
            view, "Deleted '${deletedItem.title}'", Snackbar.LENGTH_LONG
        )

        snackBar.setAction("Undo"){
            todoViewModel.insertTodo(deletedItem)
        }

        snackBar.show()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun swipeToDelete(recyclerView: RecyclerView){
        val swipeToDeleteCallback = object : SwipeToDelete(){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.absoluteAdapterPosition
                val deletedItem = adapter.todoList[position]
                todoViewModel.deleteTodo(deletedItem)
                adapter.notifyItemRemoved(position)

                restoreDeletedData(viewHolder.itemView, deletedItem)

            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.menu_delete_all -> confirmeDeleteAll()
            R.id.menu_priority_high -> {
                todoViewModel.getSortByHighPriority().observe(viewLifecycleOwner, { data ->
                    adapter.setData(data)
                    binding.emptyDatabaseValue = data.isEmpty()
                })
            }
            R.id.menu_priority_low -> {
                todoViewModel.getSortByLowPriority().observe(viewLifecycleOwner, { data ->
                    adapter.setData(data)
                    binding.emptyDatabaseValue = data.isEmpty()
                })
            }
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

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null){
            searchThroughDatabases(query)
        }

        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null){
            searchThroughDatabases(query)
        }

        return true
    }

    private fun searchThroughDatabases(query: String) {
        val searchQuery = "%$query%"

        todoViewModel.searchDatabase(searchQuery).observeOnce(viewLifecycleOwner){
            it?.let { adapter.setData(it) }
        }

    }

}