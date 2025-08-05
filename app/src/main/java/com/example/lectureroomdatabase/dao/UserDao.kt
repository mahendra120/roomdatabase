package com.example.lectureroomdatabase.dao

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.lectureroomdatabase.model.User

@Dao
interface UserDao {

    @Insert
    suspend fun addUser(user: User)

    @Query("select * from user where email=(:email) AND password=(:password)")
    suspend fun isUserFound(email: String, password: String): User?

    @Query("select * from user")
    suspend fun getAllUser(): List<User>

    @Delete
    suspend fun deleteUser(user: User)

    @Update
    suspend fun updateUser(user: User)

}