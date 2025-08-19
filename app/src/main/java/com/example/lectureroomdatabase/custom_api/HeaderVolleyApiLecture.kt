package com.example.lectureroomdatabase.custom_api

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.rounded.ThumbUp
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.integration.compose.CrossFade
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.example.lectureroomdatabase.custom_api.ui.theme.LecchrComposeTheme
import com.example.lectureroomdatabase.model.TodoData
import com.example.lectureroomdatabase.model.Todos
import com.example.lectureroomdatabase.model.UserData
import com.example.lectureroomdatabase.model.UserModel
import com.google.gson.Gson
import com.google.gson.JsonObject
import org.json.JSONObject

class HeaderVolleyApiLecture : ComponentActivity() {
    val list = mutableStateListOf<UserData>()
    var isShowDialog by mutableStateOf(false)
    var editUser: UserData? = null

    @OptIn(ExperimentalGlideComposeApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        fetchData()

        setContent {
            LecchrComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize(), floatingActionButton = {
                    FloatingActionButton(onClick = {
                        isShowDialog = true
                    }) {
                        Icon(Icons.Default.Add, contentDescription = null)
                    }
                }) { innerPadding ->

                    if (isShowDialog) {
                        showAddUserDialog()
                    }

                    LazyColumn(modifier = Modifier.padding(innerPadding)) {

                        items(list.size) {
                            ElevatedCard(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.DarkGray)
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .weight(1f)
                                    ) {
                                        Text(
                                            list[it].name,
                                            modifier = Modifier.padding(
                                                horizontal = 20.dp,
                                                vertical = 5.dp
                                            )
                                        )
                                        Text(
                                            list[it].email,
                                            modifier = Modifier.padding(
                                                horizontal = 20.dp,
                                                vertical = 5.dp
                                            )
                                        )
                                    }
//                                    Spacer(modifier = Modifier.fillMaxWidth().weight(1f))
                                    IconButton(onClick = {
                                        editUser = list[it]
                                        isShowDialog = true
                                    }) {
                                        Icon(
                                            Icons.Default.Edit,
                                            contentDescription = null,
                                            tint = Color.White
                                        )
                                    }
                                    IconButton(onClick = {
                                        deleteUser(list[it].id)
                                    }) {
                                        Icon(
                                            Icons.Default.Delete,
                                            contentDescription = null,
                                            tint = Color.White
                                        )
                                    }
                                }
                            }
                        }

                    }
                }
            }
        }
    }

    @Composable
    private fun showAddUserDialog() {
        var name by remember { mutableStateOf(editUser?.name ?: "") }
        var email by remember { mutableStateOf(editUser?.email ?: "") }
        var password by remember { mutableStateOf(editUser?.pasword ?: "") }

        Dialog(onDismissRequest = {
            editUser = null
            isShowDialog = false
        }) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    )
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    )
                )
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Next
                    )
                )
                ElevatedButton(onClick = {
                    editUser?.let {
                        updateUser(name, email, password, it.id)
                    } ?: run {
                        addUser(name, email, password)
                    }
                    isShowDialog = false
                }) {
                    Text("Submit")
                }
            }
        }
    }

    fun addUser(name: String, email: String, password: String) {
        var data = JSONObject()
        data.put("name", name)
        data.put("email", email)
        data.put("pasword", password)

        val url = "https://generateapi.onrender.com/api/user"
        val stringRequest = object : JsonObjectRequest(
            Request.Method.POST, url, data,
            Response.Listener<JSONObject> { response ->
                Log.d("=====", "fetchData: $response")
                fetchData()
            },
            Response.ErrorListener {
                Log.d("=====", "error: ${it.networkResponse.data.toString()}")
            }) {
            override fun getHeaders(): Map<String?, String?>? {
                return mapOf(
                    Pair("Authorization", "Z92yyuZjXdErhMnK")
                )
            }

            override fun getBodyContentType(): String? {
                return "application/json"
            }

//            override fun getBody(): ByteArray? {
//                var data = JSONObject()
//                data.put("name", name)
//                data.put("email", email)
//                data.put("password", password)
//                return data.toString().toByteArray(charset = Charsets.UTF_8)
//            }

//            override fun getParams(): Map<String?, String?>? {
//                return mapOf(
//                    Pair("name", name),
//                    Pair("email", email),
//                    Pair("pasword", password),
//                )
//            }
        }
        val queue = Volley.newRequestQueue(this)
        queue.add(stringRequest)
    }

    fun updateUser(name: String, email: String, password: String, id: String) {
        var data = JSONObject()
        data.put("name", name)
        data.put("email", email)
        data.put("pasword", password)

        val url = "https://generateapi.onrender.com/api/user/$id"
        val stringRequest = object : JsonObjectRequest(
            Request.Method.PATCH, url, data,
            Response.Listener<JSONObject> { response ->
                Log.d("=====", "fetchData: $response")
                fetchData()
            },
            Response.ErrorListener {
                Log.d("=====", "error: ${it.networkResponse.data.toString()}")
            }) {
            override fun getHeaders(): Map<String?, String?>? {
                return mapOf(
                    Pair("Authorization", "Z92yyuZjXdErhMnK")
                )
            }

            override fun getBodyContentType(): String? {
                return "application/json"
            }

//            override fun getBody(): ByteArray? {
//                var data = JSONObject()
//                data.put("name", name)
//                data.put("email", email)
//                data.put("password", password)
//                return data.toString().toByteArray(charset = Charsets.UTF_8)
//            }

//            override fun getParams(): Map<String?, String?>? {
//                return mapOf(
//                    Pair("name", name),
//                    Pair("email", email),
//                    Pair("pasword", password),
//                )
//            }
        }
        val queue = Volley.newRequestQueue(this)
        queue.add(stringRequest)
    }

    fun deleteUser(id: String) {
        val url = "https://generateapi.onrender.com/api/user/$id"
        val stringRequest = object : StringRequest(
            Request.Method.DELETE, url,
            Response.Listener<String> { response ->
                Log.d("=====", "fetchData: $response")
                fetchData()
            },
            Response.ErrorListener {
                Log.d("=====", "error: ${it.networkResponse.data.toString()}")
            }) {
            override fun getHeaders(): Map<String?, String?>? {
                return mapOf(
                    Pair("Authorization", "Z92yyuZjXdErhMnK")
                )
            }
        }
        val queue = Volley.newRequestQueue(this)
        queue.add(stringRequest)
    }

    fun fetchData() {
        val url = "https://generateapi.onrender.com/api/user"
        val stringRequest = object : StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                Log.d("=====", "fetchData: $response")

                var userMode: UserModel = Gson().fromJson(response, UserModel::class.java)
                userMode.users.forEach {
                    Log.d("=====", "fetchData: -> $it")
                }
                list.clear()
                list.addAll(userMode.users)
            },
            Response.ErrorListener {
                Log.d("=====", "error: ${it.localizedMessage}")
            }) {
            override fun getHeaders(): Map<String?, String?>? {
                return mapOf(
                    Pair("Authorization", "Z92yyuZjXdErhMnK")
                )
            }
        }
        val queue = Volley.newRequestQueue(this)
        queue.add(stringRequest)
    }

}

/*
*   header api crud operations
* CRUD
* C -> Create
* R -> Read
* U -> Update
* D -> Delete
*
* Retrofit?
*
* */