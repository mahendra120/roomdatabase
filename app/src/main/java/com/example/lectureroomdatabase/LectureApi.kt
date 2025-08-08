package com.example.lectureroomdatabase

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.lectureroomdatabase.ui.theme.LectureRoomDatabaseTheme
import org.json.JSONArray
import org.json.JSONObject

class LectureApi : ComponentActivity() {

    var apiResponse by mutableStateOf("")
    var todoList by mutableStateOf<JSONArray?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        fetchData()

        setContent {
            LectureRoomDatabaseTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    todoList?.let {
                        LazyColumn {
                            items(it.length()) { index ->
                                val todo = it.getJSONObject(index)
                                val name = todo.getString("todo")
                                Text(name, modifier = Modifier.padding(15.dp))
                            }
                        }
                    } ?: run {
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
        // Instantiate the RequestQueue.
        val url = "https://dummyjson.com/todos"

        // Request a string response from the provided URL.
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                apiResponse = response

                val data = JSONObject(apiResponse)
                val total = data.getInt("total")

//                Log.d("=====", "fetchData: total = $total")

                val todos = data.getJSONArray("todos")
                todoList = todos
                for (i in 0 until todos.length()) {
                    val todo = todos.getJSONObject(i)
                    val name = todo.getString("todo")
                    Log.d("=====", "fetchData: total = $name")
                }

            },
            Response.ErrorListener {
                apiResponse = "That didn't work!"
            })

        val queue = Volley.newRequestQueue(this)
        // Add the request to the RequestQueue.
        queue.add(stringRequest)
    }

}
