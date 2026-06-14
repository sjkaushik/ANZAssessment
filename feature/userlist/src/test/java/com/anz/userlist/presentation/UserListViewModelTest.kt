package com.anz.userlist.presentation

import app.cash.turbine.test
import com.anz.common.result.Resource
import com.anz.domain.model.UserResponse
import com.anz.domain.usecase.GetUsersUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class UsersViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    @Mock
    private lateinit var getUsersUseCase: GetUsersUseCase

    private lateinit var viewModel: UsersViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getUsers success updates state with users`() = runTest {
        val users = listOf(UserResponse(1, "Name", "Username", "email@test.com"))
        whenever(getUsersUseCase()).thenReturn(flowOf(Resource.Success(users)))

        viewModel = UsersViewModel(getUsersUseCase)

        viewModel.state.test {
            val state = awaitItem()
            assertEquals(users, state.users)
            assertEquals(false, state.isLoading)
            assertEquals(null, state.error)
        }
    }

    @Test
    fun `getUsers error updates state with error message`() = runTest {
        val errorMessage = "Error message"
        whenever(getUsersUseCase()).thenReturn(flowOf(Resource.Error(errorMessage)))

        viewModel = UsersViewModel(getUsersUseCase)

        viewModel.state.test {
            val state = awaitItem()
            assertEquals(errorMessage, state.error)
            assertEquals(false, state.isLoading)
        }
    }

    @Test
    fun `getUsers loading updates state with loading true`() = runTest {
        whenever(getUsersUseCase()).thenReturn(flowOf(Resource.Loading()))

        viewModel = UsersViewModel(getUsersUseCase)

        viewModel.state.test {
            val state = awaitItem()
            assertEquals(true, state.isLoading)
        }
    }
}
