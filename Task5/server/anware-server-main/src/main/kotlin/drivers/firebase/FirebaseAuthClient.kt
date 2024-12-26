package org.anware.drivers.firebase

import org.anware.core.exeptions.InvalidLoginCredentialsException
import org.anware.core.exeptions.InvalidRefreshTokenException
import org.anware.data.dto.firebase.FirebaseSignInRequest
import org.anware.data.dto.firebase.FirebaseSignInResponse
import org.anware.data.dto.firebase.RefreshTokenRequest
import org.anware.data.dto.firebase.RefreshTokenResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestClient
import org.springframework.web.util.UriBuilder

@Component
class FirebaseAuthClient{

    @Value("\${firebase.web-app.api_key}")
    private val webApiKey: String? = null

    @Value("\${firebase.web-app.sign-in-base-url}")
    private lateinit var singInBaseURL: String

    @Value("\${firebase.web-app.api-key-param}")
    private lateinit var apiKeyParam: String

    @Value("\${firebase.web-app.refresh-token-grant-type}")
    private lateinit var refreshTokenGrantType: String

    @Value("\${firebase.web-app.refresh-token-base-url}")
    private lateinit var refreshTokenBaseURL: String

    fun login(emailId: String, password: String): FirebaseSignInResponse? {
        val requestBody = FirebaseSignInRequest(emailId, password, true)
        return sendSignInRequest(requestBody)
    }

    private fun sendSignInRequest(firebaseSignInRequest: FirebaseSignInRequest): FirebaseSignInResponse? {
        try {
            return RestClient.create(singInBaseURL)
                .post()
                .uri { uriBuilder: UriBuilder ->
                    uriBuilder
                        .queryParam(apiKeyParam, webApiKey)
                        .build()
                }
                .body(firebaseSignInRequest)
                .contentType(MediaType.APPLICATION_JSON)
                .retrieve()
                .body<FirebaseSignInResponse>(FirebaseSignInResponse::class.java)
        } catch (exception: HttpClientErrorException) {
            if (exception.responseBodyAsString.contains("INVALID_LOGIN_CREDENTIALS")) {
                throw InvalidLoginCredentialsException("Invalid login credentials provided")
            }
            throw exception
        }
    }

    fun exchangeRefreshToken(refreshToken: String): RefreshTokenResponse? {
        val requestBody = RefreshTokenRequest(refreshTokenGrantType, refreshToken)
        return sendRefreshTokenRequest(requestBody)
    }

    private fun sendRefreshTokenRequest(refreshTokenRequest: RefreshTokenRequest): RefreshTokenResponse? {
        try {
            return RestClient.create(refreshTokenBaseURL)
                .post()
                .uri { uriBuilder: UriBuilder ->
                    uriBuilder
                        .queryParam(apiKeyParam, webApiKey)
                        .build()
                }
                .body(refreshTokenRequest)
                .contentType(MediaType.APPLICATION_JSON)
                .retrieve()
                .body<RefreshTokenResponse>(RefreshTokenResponse::class.java)
        } catch (exception: HttpClientErrorException) {
            if (exception.responseBodyAsString.contains(INVALID_REFRESH_TOKEN_ERROR)) {
                throw InvalidRefreshTokenException("Invalid refresh token provided")
            }
            throw exception
        }
    }

    companion object{
        private const val INVALID_REFRESH_TOKEN_ERROR: String = "INVALID_REFRESH_TOKEN"
    }

}

