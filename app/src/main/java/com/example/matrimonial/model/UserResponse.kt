package com.example.matrimonial.model

data class UserResponse(
    val results: List<User>
)

data class User(

    val gender: String,
    val name: Name,
    val location: Location,
    val email: String,
    val dob: Dob,
    val phone: String,
    val cell: String,
    val picture: Picture
)

data class Name(val title: String, val first: String, val last: String)
data class Location(val country: String)
data class Dob(val date: String, val age: Int)
data class Picture(val large: String)