package com.example.lectureroomdatabase.model

data class Todos (
  var id        : Int?     = null,
  var todo      : String?  = null,
  var completed : Boolean? = null,
  var userId    : Int?     = null
)

//data class Todos (
//
//  @SerializedName("id"        ) var id        : Int?     = null,
//  @SerializedName("todo"      ) var todo      : String?  = null,
//  @SerializedName("completed" ) var completed : Boolean? = null,
//  @SerializedName("userId"    ) var userId    : Int?     = null
//
//)