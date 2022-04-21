package com.example.mytodoapp

import android.os.Bundle
import android.view.*
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mytodoapp.adapter.TodoAdapter
import com.example.mytodoapp.databinding.FragmentToDoBinding
import com.example.mytodoapp.dialogs.AlertDialog
import com.example.mytodoapp.entities.Todo
import com.example.mytodoapp.models.TodoModel
import com.example.mytodoapp.prefdata.LoginPreference
import kotlinx.coroutines.launch

class ToDoFragment : Fragment() {
    private val modelView : TodoModel by activityViewModels()
    private lateinit var binding: FragmentToDoBinding
    private lateinit var loginPreference: LoginPreference
    private var userId : Long = 0
    val adapter = TodoAdapter(::onComplete)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.itemLogout -> {
                lifecycle.coroutineScope.launch {
                    loginPreference.setLoginStatus(false, context = requireContext())
                }
            }
            R.id.itemCompleted -> {
                lifecycle.coroutineScope.launch {
                    modelView
                        .getAllTodoByCompleteCheck(userId, true)
                        .observe(viewLifecycleOwner) {
                            adapter.submitList(it)
                        }
                }
            }
            R.id.itemIncompleted -> {
                lifecycle.coroutineScope.launch {
                    modelView
                        .getAllTodoByCompleteCheck(userId, false)
                        .observe(viewLifecycleOwner) {
                            adapter.submitList(it)
                        }
                }
            }
            R.id.itemLow -> {
                lifecycle.coroutineScope.launch {
                    modelView
                        .getAllTodoByPriorityCheck(userId, "Low")
                        .observe(viewLifecycleOwner) {
                            adapter.submitList(it)
                        }
                }
            }
            R.id.itemNormal -> {
                lifecycle.coroutineScope.launch {
                    modelView
                        .getAllTodoByPriorityCheck(userId, "Normal")
                        .observe(viewLifecycleOwner) {
                            adapter.submitList(it)
                        }
                }
            }
            R.id.itemHigh -> {
                lifecycle.coroutineScope.launch {
                    modelView
                        .getAllTodoByPriorityCheck(userId, "High")
                        .observe(viewLifecycleOwner) {
                            adapter.submitList(it)
                        }
                }
            }
            R.id.itemAll -> {
                lifecycle.coroutineScope.launch {
                    modelView
                        .getAllTodoByUser(userId)
                        .observe(viewLifecycleOwner) {
                            adapter.submitList(it)
                        }
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loginPreference = LoginPreference(requireContext())
        binding = FragmentToDoBinding.inflate(inflater, container,false)
        loginPreference.isLoggedInFlow.asLiveData().observe(viewLifecycleOwner){
            if(!it){
                findNavController().navigate(R.id.action_toDoFragment_to_loginFragment)
            }
        }


        binding.todoRecycleView.layoutManager = LinearLayoutManager(activity)
        binding.todoRecycleView.adapter = adapter


        loginPreference.userIdFlow.asLiveData().observe(viewLifecycleOwner){
            userId = it
            modelView.getAllTodoByUser(it).observe(viewLifecycleOwner){
                val todoList = mutableListOf<Todo>()
                it.forEach {
                    if(!it.isDone){
                        todoList.add(it)
                    }
                }
                adapter.submitList(todoList)

            }
        }


        binding.newToDoButton.setOnClickListener {
            findNavController().navigate(R.id.action_toDoFragment_to_newToDoFragment)
        }

        return binding.root
    }

    private fun onComplete(todo : Todo, action : String){
        when(action){
            "Done" -> {
                todo.isDone = true
                modelView.updateTodo(todo)
            }
            "Delete" -> {
                AlertDialog(
                    title = "Delete ${todo.name}?",
                    body = "Are you sure to delete?",
                    positiveText = "Yes",
                    negativeText = "Cancel"
                ){
                    modelView.deleteTodo(todo)
                }.show(childFragmentManager, null)
            }
        }
    }

}