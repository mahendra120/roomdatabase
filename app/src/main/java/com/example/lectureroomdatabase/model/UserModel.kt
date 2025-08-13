package com.example.lectureroomdatabase.model

import com.google.gson.annotations.SerializedName


data class UserModel(

    @SerializedName("Status") var status: String = "",
    @SerializedName("Message") var message: String = "",
    @SerializedName("Data") var users: ArrayList<UserData> = arrayListOf()

)

data class UserData(

    @SerializedName("_id") var id: String = "",
    @SerializedName("name") var name: String = "",
    @SerializedName("email") var email: String = "",
    @SerializedName("pasword") var pasword: String = ""

)