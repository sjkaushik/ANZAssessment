package com.anz.userdetails.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.anz.common.components.ErrorComponent
import com.anz.common.components.LoadingComponent

@Composable
fun UserDetailsScreen(viewModel: UserDetailsViewModel = hiltViewModel()) {

    val state by viewModel.state.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        state.user?.let { user ->
            Column(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = "User Details",
                    style = MaterialTheme.typography.headlineMedium
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Name: ${user.name}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "Username: ${user.username}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "Email: ${user.email}",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        if (state.error != null) {
            ErrorComponent(
                message = state.error!!,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        if (state.isLoading) {
            LoadingComponent(modifier = Modifier.align(Alignment.Center))
        }
    }

}