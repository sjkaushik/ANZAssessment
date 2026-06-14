package com.anz.userlist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anz.common.result.Resource
import com.anz.domain.usecase.GetUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject
@HiltViewModel
class UsersViewModel @Inject constructor(private val getUsersUseCase: GetUsersUseCase) : ViewModel() {

    private val _state = MutableStateFlow(UsersContract.State())
    val state = _state.asStateFlow()

    init {
        onEvent(UsersContract.Event.LoadUsers)
    }

    fun onEvent(event: UsersContract.Event) {
        when (event) {
            is UsersContract.Event.LoadUsers -> getUsers()
            is UsersContract.Event.OnUserClick -> {
                // Trigger navigation effect if needed or handle logic
            }
        }
    }

    private fun getUsers() {
        getUsersUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.update { it.copy(isLoading = false, users = result.data ?: emptyList(), error = null) }
                }
                is Resource.Error -> {
                    _state.update { it.copy(isLoading = false, error = result.message ?: "An unexpected error occurred") }
                }
                is Resource.Loading -> {
                    _state.update { it.copy(isLoading = true) }
                }
            }
        }.launchIn(viewModelScope)
    }

}