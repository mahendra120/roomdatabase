package com.example.lectureroomdatabase.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.lectureroomdatabase.model.User

@Dao
interface UserDao {

    @Insert
    suspend fun addUser(user : User)

}