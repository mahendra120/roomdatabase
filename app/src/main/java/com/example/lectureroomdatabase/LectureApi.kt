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
import androidx.compose.material3.Card
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
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.lectureroomdatabase.model.Product
import com.example.lectureroomdatabase.model.ProductModel
import com.example.lectureroomdatabase.model.TodoData
import com.example.lectureroomdatabase.model.Todos
import com.example.lectureroomdatabase.ui.theme.LectureRoomDatabaseTheme
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONObject

class LectureApi : ComponentActivity() {

    var mainProductList = ArrayList<Product>(0)
    val productList = mutableStateListOf<Product>()
//    var categoryList = mutableStateListOf<String>()

    @OptIn(ExperimentalGlideComposeApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        fetchData()
        setContent {
            LectureRoomDatabaseTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    if (categoryList.isNotEmpty()) {
//                        Box(
//                            modifier = Modifier
//                                .fillMaxSize()
//                                .padding(innerPadding)
//                        ) {
//                            LazyColumn(contentPadding = PaddingValues(15.dp)) {
//                                items(categoryList.size) { index ->
//                                    val categoryName = categoryList[index]
//                                    Text(categoryName)
//                                    Spacer(modifier = Modifier.height(20.dp))
//
//                                }
//                            }
//                        }
//                    } else {
                    if (productList.isNotEmpty()) {
                        LazyColumn(contentPadding = PaddingValues(15.dp)) {
                            items(productList.size) { index ->
                                val product = productList[index]
                                Card {
                                    GlideImage(product.thumbnail, contentDescription = null)
                                    Text(product.category)
                                }
                                Spacer(modifier = Modifier.height(20.dp))
                            }
                        }
                    } else {
                        Box(
                            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "loading...", modifier = Modifier.padding(innerPadding)
                            )
                        }
                    }
                }
            }
        }
    }

    fun fetchData() {
        val url = "https://dummyjson.com/products?limit=194"
        val stringRequest =
            StringRequest(Request.Method.GET, url, Response.Listener<String> { response ->
                var apiResponse = response
                val productMode = Gson().fromJson(response.toString(), ProductModel::class.java)
                // way-1
                productList.clear()
//                productList.addAll(productMode.products)
//                productList.forEach {
//                    if (!categoryList.contains(it.category)) {
//                        categoryList.add(it.category)
//                    }
//                }

                // way-2
//                val categorySet = mutableSetOf<String>()
//                val mainProductList = productMode.products
//                mainProductList.forEach {
//                    categorySet.add(it.category)
////                    Log.d("=====", "fetchData: $categorySet")
//                }
//                categoryList.addAll(categorySet)

                // way-3
                mainProductList = productMode.products
                productList.addAll(mainProductList.distinctBy { it.category })

            }, Response.ErrorListener {
                Log.d("=====", "fetchData: That didn't work!")
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