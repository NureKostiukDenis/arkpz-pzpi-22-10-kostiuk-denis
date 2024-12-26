package org.anware.data.dto

data class CreateUserRequest(
    val userName: String,
    val email: String,
    val password: String
)
