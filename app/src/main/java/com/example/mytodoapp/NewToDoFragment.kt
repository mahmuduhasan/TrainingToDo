package com.example.mytodoapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.example.mytodoapp.databinding.FragmentNewToDoBinding
import com.example.mytodoapp.dialogs.DatePicker
import com.example.mytodoapp.dialogs.TimePicker
import com.example.mytodoapp.entities.Todo
import com.example.mytodoapp.models.TodoModel
import com.example.mytodoapp.prefdata.LoginPreference

class NewToDoFragment : Fragment() {
    private lateinit var loginPreference : LoginPreference
    private val modelView : TodoModel by activityViewModels()
    private lateinit var binding: FragmentNewToDoBinding
    private var priority = "Normal"
    private var userId = 0L
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loginPreference = LoginPreference(requireContext())
        loginPreference.userIdFlow.asLiveData().observe(viewLifecycleOwner){
            if(it > 0){
                userId = it
            }
        }
        binding = FragmentNewToDoBinding.inflate(inflater,container,false)
        binding.priorityRG.setOnCheckedChangeListener { radioGroup, i ->
            val selectedRB : RadioButton = radioGroup.findViewById(i)
            priority = selectedRB.text.toString()
        }

        binding.datePickerButton.setOnClickListener {
            DatePicker{
                binding.dateShowTV.text = it
            }.show(childFragmentManager, "date-picker")
        }

        binding.timePickerButton.setOnClickListener {
            TimePicker{
                binding.timeShowTV.text = it
            }.show(childFragmentManager, "time-picker")
        }

        binding.addButton.setOnClickListener {
            val name = binding.todoName.text.toString()
            val date = binding.dateShowTV.text.toString()
            val time = binding.timeShowTV.text.toString()

            val newTodo = Todo(
                userId = userId,
                name = name,
                date = date,
                time = time,
                priority = priority
            )
            modelView.insertTodo(newTodo)

            findNavController().navigate(R.id.action_newToDoFragment_to_toDoFragment)
        }

        return binding.root
    }

}