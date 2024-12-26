package org.anware.presentation.handler

import org.anware.data.dto.BadResponse
import org.anware.core.exeptions.AccountAlreadyExistsException
import org.anware.core.exeptions.UserHasWarehouseException
import org.anware.domain.handler.ApiKeyInvalidException
import org.apache.coyote.BadRequestException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.client.HttpClientErrorException.Conflict

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

    @ExceptionHandler(ApiKeyInvalidException::class)
    fun handleApiKeyInvalid(exception: AccountAlreadyExistsException): ResponseEntity<BadResponse> {
        val response = BadResponse(
            status = "unsuccessful",
            message = exception.message ?: "An error occurred"
        )
        return ResponseEntity(response, HttpStatus.FORBIDDEN)
    }

    @ExceptionHandler(UserHasWarehouseException::class)
    fun handleUserHasWarehouseException(exception: UserHasWarehouseException): ResponseEntity<BadResponse> {
        val response = BadResponse(
            status = "unsuccessful",
            message = exception.message ?: "An error occurred"
        )
        return ResponseEntity(response, HttpStatus.CONFLICT)
    }
}