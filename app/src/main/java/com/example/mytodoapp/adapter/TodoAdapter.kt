package com.example.mytodoapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mytodoapp.databinding.TodoViewBinding
import com.example.mytodoapp.entities.Todo

class TodoAdapter : ListAdapter<Todo, TodoAdapter.TodoViewHolder>(TodoDiffUtil()) {
    class TodoViewHolder(val binding: TodoViewBinding) : RecyclerView.ViewHolder(binding.root){
        fun todoBind(todo: Todo){
            binding.todo = todo
        }
    }

    class TodoDiffUtil : DiffUtil.ItemCallback<Todo>() {
        override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
            return oldItem.todoId == newItem.todoId
        }

        override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val binding = TodoViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val todo = getItem(position)
        holder.todoBind(todo)
    }
}