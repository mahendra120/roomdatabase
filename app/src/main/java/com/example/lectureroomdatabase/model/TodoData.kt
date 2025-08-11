package com.example.lectureroomdatabase.model

data class TodoData (

  var todos : ArrayList<Todos> = arrayListOf(),
  var total : Int?             = null,
  var skip  : Int?             = null,
  var limit : Int?             = null

)

//data class TodoData (
//
//  @SerializedName("todos" ) var todos : ArrayList<Todos> = arrayListOf(),
//  @SerializedName("total" ) var total : Int?             = null,
//  @SerializedName("skip"  ) var skip  : Int?             = null,
//  @SerializedName("limit" ) var limit : Int?             = null
//
//)