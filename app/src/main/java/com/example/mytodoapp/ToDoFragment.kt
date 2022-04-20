package com.example.mytodoapp

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mytodoapp.adapter.TodoAdapter
import com.example.mytodoapp.databinding.FragmentToDoBinding
import com.example.mytodoapp.entities.Todo
import com.example.mytodoapp.models.TodoModel
import com.example.mytodoapp.prefdata.LoginPreference
import kotlinx.coroutines.launch

class ToDoFragment : Fragment() {
    private val modelView : TodoModel by activityViewModels()
    private lateinit var binding: FragmentToDoBinding
    private lateinit var loginPreference: LoginPreference

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
        val adapter = TodoAdapter(::onComplete)

        binding.todoRecycleView.layoutManager = LinearLayoutManager(activity)
        binding.todoRecycleView.adapter = adapter

        loginPreference.userIdFlow.asLiveData().observe(viewLifecycleOwner){
            modelView.getAllTodoByUser(it).observe(viewLifecycleOwner){
                val todoList = mutableListOf<Todo>()
                for(todo in it){
                    if(!todo.isDone){
                        todoList.add(todo)
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
                modelView.deleteTodo(todo)
            }
        }
    }

}