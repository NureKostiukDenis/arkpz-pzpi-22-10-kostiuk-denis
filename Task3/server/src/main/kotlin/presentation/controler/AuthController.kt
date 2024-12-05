package org.anware.presentation.controler

import org.anware.data.service.UserService
import org.anware.drivers.firebase.FirebaseAuthClient
import org.anware.drivers.firebase.InvalidLoginCredentialsException
import org.anware.drivers.firebase.InvalidRefreshTokenException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/user")
class AuthController @Autowired constructor(
    private val userService: UserService,
    private val firebaseAuthClient: FirebaseAuthClient
) {

    @PostMapping("/create")
    fun createUser(
        @RequestParam email: String,
        @RequestParam password: String
    ): ResponseEntity<String> {
        userService.create(email, password)
        return ResponseEntity("User created successfully", HttpStatus.CREATED)
    }

    @PostMapping("/log-in")
    fun logInUser(
        @RequestParam email: String,
        @RequestParam password: String
    ): ResponseEntity<Any> {
        val signInResponse = firebaseAuthClient.login(email, password)
        return ResponseEntity(signInResponse, HttpStatus.OK)
    }

    @PostMapping("/refresh-token")
    fun refreshToken(
        @RequestParam refreshToken: String
    ): ResponseEntity<Any> {
        val tokenResponse = firebaseAuthClient.exchangeRefreshToken(refreshToken)
        return ResponseEntity(tokenResponse, HttpStatus.OK)
    }

}