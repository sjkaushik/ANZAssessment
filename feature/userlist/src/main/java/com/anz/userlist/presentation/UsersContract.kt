package com.anz.userlist.presentation

import com.anz.domain.model.UserResponse

class UsersContract {

    sealed class Event {
        object LoadUsers : Event()
        data class OnUserClick(val userId: Int) : Event()
    }

    data class State(
        val isLoading: Boolean = false,
        val users: List<UserResponse> = emptyList(),
        val error: String? = null
    )

    sealed class Effect {
        data class NavigationToDetail(val userId: Int) : Effect()
    }
}