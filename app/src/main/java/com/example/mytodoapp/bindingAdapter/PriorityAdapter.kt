package com.example.mytodoapp.bindingAdapter

import android.widget.CheckBox
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.mytodoapp.R

@BindingAdapter("app:setPriorityImage")
fun setPriorityImage(imageView: ImageView, priority : String){
    val image = when(priority){
        "Low" -> R.drawable.ic_baseline_stars_low
        "Normal" -> R.drawable.ic_baseline_stars_normal
        "High" -> R.drawable.ic_baseline_stars_high
        else -> R.drawable.ic_baseline_stars_low
    }
    imageView.setImageResource(image)
}

@BindingAdapter("app:setDone")
fun setDone(checkBox: CheckBox, done: Boolean){
    val isDone = when(done){
        true -> true
        false -> false
    }
    checkBox.isChecked = isDone
}