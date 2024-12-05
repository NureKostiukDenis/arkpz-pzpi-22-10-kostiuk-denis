package org.anware.presentation.controler

import org.anware.data.dto.BadResponse
import org.anware.data.service.AccountAlreadyExistsException
import org.apache.coyote.BadRequestException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException::class)
    fun handleBadRequest(exception: BadRequestException): ResponseEntity<BadResponse> {
        val response = BadResponse(
            status = "unsuccessful",
            message = exception.message ?: "Invalid request"
        )
        return ResponseEntity(response, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(Exception::class)
    fun handleGeneralException(exception: Exception): ResponseEntity<BadResponse> {
        val response = BadResponse(
            status = "unsuccessful",
            message = exception.message ?: "An error occurred"
        )
        return ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(AccountAlreadyExistsException::class)
    fun handleAccountAlreadyExistsException(exception: AccountAlreadyExistsException): ResponseEntity<BadResponse> {
        val response = BadResponse(
            status = "unsuccessful",
            message = exception.message ?: "An error occurred"
        )
        return ResponseEntity(response, HttpStatus.CONFLICT)
    }
}