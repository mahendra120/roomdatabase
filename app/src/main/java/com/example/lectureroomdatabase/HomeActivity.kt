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
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Modifier
import com.example.lectureroomdatabase.model.User
import com.example.lectureroomdatabase.ui.theme.LectureRoomDatabaseTheme
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeActivity : ComponentActivity() {

    val list = mutableStateListOf<User>()

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        GlobalScope.launch {
            var userList = AppDatabase.getInstance(this@HomeActivity)
                .userDao()
                .getAllUser()
            list.addAll(userList)

            list.forEach {
                Log.d("=====", "onCreate: $it")
            }

        }

        setContent {
            LectureRoomDatabaseTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        modifier = Modifier.padding(innerPadding)
                    ){
                        LazyColumn {
                            items(list.size) {
                                val user = list[it]
                                ElevatedCard {
                                    Text(user.email)
                                }

                            }
                        }
                    }
                }
            }
        }
    }
}
