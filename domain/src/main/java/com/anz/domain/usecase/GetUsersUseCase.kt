package com.anz.domain.usecase

import com.anz.common.result.Resource
import com.anz.domain.model.UserResponse
import com.anz.domain.repository.UsersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(private val repository: UsersRepository) {

    operator fun invoke(): Flow<Resource<List<UserResponse>>> = flow {
        try {
            emit(Resource.Loading())
            val users = repository.getUsers()
            emit(Resource.Success(users))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }
}