package com.group.todoappweek8.viewmodel

import android.app.Application
import android.widget.ProgressBar
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.group.todoappweek8.model.Todo
import com.group.todoappweek8.model.TodoDatabase
import com.group.todoappweek8.util.buildDb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ListTodoViewModel(application: Application)
    :AndroidViewModel(application), CoroutineScope {
    val todoLD = MutableLiveData<List<Todo>>()
    val todoLoadErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()
    private var job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    fun refresh() {
        loadingLD.value = true

        todoLoadErrorLD.value = false
        launch {
            val db = buildDb(getApplication())

            todoLD.postValue(db.todoDao().selectAllTodo())
        }
    }

    fun clearTask(todo: Todo) {
        launch {
            val db = buildDb(getApplication())
            db.todoDao().deleteTodo(todo)

            todoLD.postValue(db.todoDao().selectAllTodo())
        }
    }
}



