package com.anz.domain.usecase

import com.anz.common.result.Resource
import com.anz.domain.model.UserResponse
import com.anz.domain.repository.UsersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetUserDetailUseCase @Inject constructor(private val repository: UsersRepository) {

    operator fun invoke(userId : Int) : Flow<Resource<UserResponse>> = flow {
        try {
            emit(Resource.Loading())
            val user = repository.getUserById(userId)
            emit(Resource.Success(user))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }
}