package com.example.lectureroomdatabase.custom_api

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.lectureroomdatabase.custom_api.ui.theme.LecchrComposeTheme
import com.example.lectureroomdatabase.model.TodoData
import com.example.lectureroomdatabase.model.Todos
import org.json.JSONObject

class HeaderVolleyApiLecture : ComponentActivity() {
    var apiResponse by mutableStateOf("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        fetchData()

        setContent {
            LecchrComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Text(
                        text = apiResponse,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
    
    fun fetchData() {
        val url = "https://generateapi.onrender.com/api/user"
        val stringRequest = object : StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                apiResponse = response
            },
            Response.ErrorListener {
                Log.d("=====", "fetchData: ${it.localizedMessage}")
                apiResponse = "That didn't work!"
            }){
            override fun getHeaders(): Map<String?, String?>? {
                return mapOf(
                    Pair("Authorization","Z92yyuZjXdErhMnK")
                )
            }
        }

        val queue = Volley.newRequestQueue(this)
        queue.add(stringRequest)
    }

}
