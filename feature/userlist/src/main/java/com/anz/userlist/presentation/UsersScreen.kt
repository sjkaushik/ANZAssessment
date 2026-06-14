package com.anz.userlist.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.anz.common.components.ErrorComponent
import com.anz.common.components.LoadingComponent
import com.anz.domain.model.UserResponse

@Composable
fun UsersScreen(
    viewModel: UsersViewModel = hiltViewModel(),
    onUserClick: (Int) -> Unit
) {

    val state by viewModel.state.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(state.users) { user ->
                UserListItem(
                    user = user,
                    onItemClick = { onUserClick(user.id) }
                )
            }
        }

        state.error?.let { error ->
            ErrorComponent(
                message = error,
                modifier = Modifier.align(Alignment.Center)
            )
        }


        if (state.isLoading) {
            LoadingComponent(modifier = Modifier.align(Alignment.Center))
        }
    }
}

@Composable
fun UserListItem(
    user: UserResponse,
    onItemClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick() }
            .padding(20.dp)
    ) {
        Text(
            text = user.name,
            style = MaterialTheme.typography.bodyLarge,
        )
        Text(
            text = user.email,
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )
    }
    HorizontalDivider()

}
