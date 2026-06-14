package com.anz.data.repository

import com.anz.domain.model.UserResponse
import com.anz.domain.remote.UsersApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class UsersRepositoryImplTest {

    @Mock
    private lateinit var usersApi: UsersApi

    private lateinit var repository: UsersRepositoryImpl

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        repository = UsersRepositoryImpl(usersApi)
    }

    @Test
    fun `getUsers calls api and returns users`() = runTest {
        val users = listOf(UserResponse(1, "Name", "Username", "email@test.com"))
        whenever(usersApi.getUsers()).thenReturn(users)

        val result = repository.getUsers()

        assertEquals(users, result)
    }

    @Test
    fun `getUserById calls api and returns user`() = runTest {
        val userId = 1
        val user = UserResponse(userId, "Name", "Username", "email@test.com")
        whenever(usersApi.getUserById(userId)).thenReturn(user)

        val result = repository.getUserById(userId)

        assertEquals(user, result)
    }
}
