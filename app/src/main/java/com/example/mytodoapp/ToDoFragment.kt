package com.example.mytodoapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mytodoapp.adapter.TodoAdapter
import com.example.mytodoapp.databinding.FragmentToDoBinding
import com.example.mytodoapp.models.TodoModel
import com.example.mytodoapp.prefdata.LoginPreference

class ToDoFragment : Fragment() {
    private val modelView : TodoModel by activityViewModels()
    private lateinit var binding: FragmentToDoBinding
    private lateinit var loginPreference: LoginPreference
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
        val adapter = TodoAdapter()

        binding.todoRecycleView.layoutManager = LinearLayoutManager(activity)
        binding.todoRecycleView.adapter = adapter

        modelView.getAllTodo().observe(viewLifecycleOwner){
            adapter.submitList(it)
        }

        binding.newToDoButton.setOnClickListener {
            findNavController().navigate(R.id.action_toDoFragment_to_newToDoFragment)
        }

        return binding.root
    }

}