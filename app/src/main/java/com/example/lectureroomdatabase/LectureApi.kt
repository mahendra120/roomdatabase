package com.example.lectureroomdatabase

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.lectureroomdatabase.model.TodoData
import com.example.lectureroomdatabase.model.Todos
import com.example.lectureroomdatabase.ui.theme.LectureRoomDatabaseTheme
import org.json.JSONArray
import org.json.JSONObject

class LectureApi : ComponentActivity() {

    var apiResponse by mutableStateOf("")
    var todoList = mutableStateListOf<Todos>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        fetchData()
        setContent {
            LectureRoomDatabaseTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    if (todoList.isNotEmpty()) {
                        LazyColumn(contentPadding = PaddingValues(15.dp)) {
                            items(todoList.size) { index ->
                                val todo = todoList[index]
                                Text(todo.todo ?: "")
                                Text(todo.completed.toString())
                                Text(todo.userId.toString())
                                Spacer(modifier = Modifier.height(20.dp))
                            }
                        }
                    } else {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "loading...",
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                    }
                }
            }
        }
    }

    fun fetchData() {
        val url = "https://dummyjson.com/todos"
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                apiResponse = response

                val data = JSONObject(apiResponse)
                val todos = data.getJSONArray("todos")
                val list = ArrayList<Todos>()
                for (i in 0 until todos.length()) {
                    val d = todos.getJSONObject(i)
                    val todos = Todos(
                        id = d.getInt("id"),
                        todo = d.getString("todo"),
                        completed = d.getBoolean("completed"),
                        userId = d.getInt("userId"),
                    )
                    list.add(todos)
                }
                val todoData = TodoData(
                    todos = list,
                    total = data.getInt("total"),
                    skip = data.getInt("skip"),
                    limit = data.getInt("limit"),
                )
                todoList.addAll(todoData.todos)
            },
            Response.ErrorListener {
                apiResponse = "That didn't work!"
            })

        val queue = Volley.newRequestQueue(this)
        queue.add(stringRequest)
    }

}

/*
* API :
* Postman -> api response
* header api
*
* api key : Z92yyuZjXdErhMnK
* */