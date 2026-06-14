package com.anz.domain.repository

import com.anz.domain.model.UserResponse

interface UsersRepository {

    suspend fun getUsers(): List<UserResponse>

    suspend fun getUserById(id: Int): UserResponse
}