package com.anz.data.repository

import com.anz.domain.model.UserResponse
import com.anz.domain.remote.UsersApi
import com.anz.domain.repository.UsersRepository
import javax.inject.Inject


class UsersRepositoryImpl @Inject constructor(private val usersApi: UsersApi) : UsersRepository {
    override suspend fun getUsers(): List<UserResponse> {
        return usersApi.getUsers()
    }

    override suspend fun getUserById(id: Int): UserResponse {
        return usersApi.getUserById(id)
    }
}