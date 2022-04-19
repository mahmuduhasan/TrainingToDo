package com.example.mytodoapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.mytodoapp.databinding.FragmentNewToDoBinding
import com.example.mytodoapp.dialogs.DatePicker
import com.example.mytodoapp.dialogs.TimePicker
import com.example.mytodoapp.entities.Todo
import com.example.mytodoapp.models.TodoModel

class NewToDoFragment : Fragment() {
    private val modelView : TodoModel by activityViewModels()
    private lateinit var binding: FragmentNewToDoBinding
    private var priority = "Low"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
                userId = System.currentTimeMillis(),
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