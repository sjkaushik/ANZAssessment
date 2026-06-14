package com.anz.userdetails.presentation

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.anz.common.result.Resource
import com.anz.domain.model.UserResponse
import com.anz.domain.usecase.GetUserDetailUseCase
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
class UserDetailViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    @Mock
    private lateinit var getUserDetailUseCase: GetUserDetailUseCase

    private lateinit var viewModel: UserDetailsViewModel

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
    fun `getUserDetail success updates state with user`() = runTest {
        val userId = 1
        val user = UserResponse(userId, "Name", "Username", "email@test.com")
        val savedStateHandle = SavedStateHandle(mapOf("userId" to userId))
        whenever(getUserDetailUseCase(userId)).thenReturn(flowOf(Resource.Success(user)))

        viewModel = UserDetailsViewModel(getUserDetailUseCase, savedStateHandle)

        viewModel.state.test {
            val state = awaitItem()
            assertEquals(user, state.user)
            assertEquals(false, state.isLoading)
            assertEquals(null, state.error)
        }
    }

    @Test
    fun `getUserDetail error updates state with error message`() = runTest {
        val userId = 1
        val errorMessage = "Error message"
        val savedStateHandle = SavedStateHandle(mapOf("userId" to userId))
        whenever(getUserDetailUseCase(userId)).thenReturn(flowOf(Resource.Error(errorMessage)))

        viewModel = UserDetailsViewModel(getUserDetailUseCase, savedStateHandle)

        viewModel.state.test {
            val state = awaitItem()
            assertEquals(errorMessage, state.error)
            assertEquals(false, state.isLoading)
        }
    }

    @Test
    fun `getUserDetail loading updates state with loading true`() = runTest {
        val userId = 1
        val savedStateHandle = SavedStateHandle(mapOf("userId" to userId))
        whenever(getUserDetailUseCase(userId)).thenReturn(flowOf(Resource.Loading()))

        viewModel = UserDetailsViewModel(getUserDetailUseCase, savedStateHandle)

        viewModel.state.test {
            val state = awaitItem()
            assertEquals(true, state.isLoading)
        }
    }
}
