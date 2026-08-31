package com.example.lab24

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

data class Todo(
    val id: Int,
    val title: String,
    val description: String?,
    val date: Long
)

class TodoViewModel(
    private  val context: Context
): ViewModel(){
    private  val todoList = mutableListOf<Todo>()

    fun insertTodo(
        title: String,
        description: String?,
        date: Long
    ) {
        var todo = Todo(
            id =  todoList.size + 1,
            title = title,
            description = description,
            date = date
        )

        todoList.add(todo)
    }

    fun getTodos(): List<Todo> {
        return  todoList
    }
}

class TodoViewModelFactory(
    private  val context: Context
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(TodoViewModel::class.java)) {
            return TodoViewModel(context) as T
        }

        throw IllegalArgumentException("not found")
    }
}