package org.anware.data.dto.firebase

data class FirebaseSignInRequest(
    val email: String,
    val password: String,
    val returnSecureToken: Boolean
)

data class FirebaseSignInResponse(
    val idToken: String,
    val refreshToken: String,
)
