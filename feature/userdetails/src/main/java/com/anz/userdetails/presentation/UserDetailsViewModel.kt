package com.anz.userdetails.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anz.common.result.Resource
import com.anz.domain.usecase.GetUserDetailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class UserDetailsViewModel @Inject constructor(
    private val getUserDetailUseCase: GetUserDetailUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(UserDetailContract.State())
    val state = _state.asStateFlow()

    init {
        savedStateHandle.get<Int>("userId")?.let { userId ->
            onEvent(UserDetailContract.Event.LoadUserDetail(userId))
        }
    }

    fun onEvent(event: UserDetailContract.Event) {
        when (event) {
            is UserDetailContract.Event.LoadUserDetail -> getUserDetail(event.userId)
        }
    }

    private fun getUserDetail(userId: Int) {
        getUserDetailUseCase(userId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.update { it.copy(isLoading = false, user = result.data, error = null) }
                }

                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = result.message ?: "An unexpected error occurred"
                        )
                    }
                }

                is Resource.Loading -> {
                    _state.update { it.copy(isLoading = true) }
                }
            }
        }.launchIn(viewModelScope)
    }
}