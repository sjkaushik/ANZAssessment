package com.anz.userdetails.presentation

import com.anz.domain.model.UserResponse

class UserDetailContract {

    sealed class Event {
        data class LoadUserDetail(val userId: Int) : Event()
    }

    data class State(
        val isLoading: Boolean = false,
        val user: UserResponse? = null,
        val error: String? = null
    )
}