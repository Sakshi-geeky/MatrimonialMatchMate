package com.example.matrimonial.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(

    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val age: Int,
    val country: String,
    val email: String,
    val imageUrl: String,
    val education: String,
    val religion: String,
    val matchScore: Int,
    var status: String // accepted / declined / none
)