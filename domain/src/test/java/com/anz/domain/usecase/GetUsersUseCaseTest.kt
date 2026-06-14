package com.anz.domain.usecase

import app.cash.turbine.test
import com.anz.common.result.Resource
import com.anz.domain.model.UserResponse
import com.anz.domain.repository.UsersRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever
import java.io.IOException

@ExperimentalCoroutinesApi
class GetUsersUseCaseTest {

    @Mock
    private lateinit var repository: UsersRepository

    private lateinit var getUsersUseCase: GetUsersUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        getUsersUseCase = GetUsersUseCase(repository)
    }

    @Test
    fun `invoke emits loading then success when repository returns users`() = runTest {
        val users = listOf(UserResponse(1, "Name", "Username", "email@test.com"))
        whenever(repository.getUsers()).thenReturn(users)

        getUsersUseCase().test {
            val loadingItem = awaitItem()
            assert(loadingItem is Resource.Loading)

            val successItem = awaitItem()
            assert(successItem is Resource.Success)
            assertEquals(users, successItem.data)

            awaitComplete()
        }
    }

    @Test
    fun `invoke emits loading then error when repository throws IOException`() = runTest {
        val errorMessage = "Couldn't reach server. Check your internet connection."
        whenever(repository.getUsers()).thenAnswer { throw IOException() }

        getUsersUseCase().test {
            val loadingItem = awaitItem()
            assert(loadingItem is Resource.Loading)

            val errorItem = awaitItem()
            assert(errorItem is Resource.Error)
            assertEquals(errorMessage, errorItem.message)

            awaitComplete()
        }
    }

    @Test
    fun `invoke emits loading then error when repository throws generic Exception`() = runTest {
        val exceptionMessage = "Something went wrong"
        whenever(repository.getUsers()).thenAnswer { throw Exception(exceptionMessage) }

        getUsersUseCase().test {
            val loadingItem = awaitItem()
            assert(loadingItem is Resource.Loading)

            val errorItem = awaitItem()
            assert(errorItem is Resource.Error)
            assertEquals(exceptionMessage, errorItem.message)

            awaitComplete()
        }
    }
}
