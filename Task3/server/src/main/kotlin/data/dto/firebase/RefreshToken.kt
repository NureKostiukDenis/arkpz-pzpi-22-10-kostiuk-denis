package org.anware.data.dto.firebase

data class RefreshTokenRequest(
    val grantType: String,
    val refreshToken: String
)

data class RefreshTokenResponse(
    val idToken: String
)