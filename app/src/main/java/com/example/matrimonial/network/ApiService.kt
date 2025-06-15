package com.example.matrimonial.network

import com.example.matrimonial.model.UserResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("api/?results=100")
    suspend fun getUsers(): Response<UserResponse>
}