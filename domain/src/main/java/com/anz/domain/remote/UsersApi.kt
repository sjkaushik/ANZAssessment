package com.anz.domain.remote

import com.anz.domain.model.UserResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface UsersApi {

    @GET("users")
    suspend fun getUsers(): List<UserResponse>

    @GET("users/{id}")
    suspend fun getUserById(@Path("id") id: Int): UserResponse
}