package org.example.core.handlers

import org.springframework.stereotype.Component

@Component
class ClientResponseHandler {

    fun handleResponse(response: String): String {
        // Обробка відповіді в залежності від статусу
        return when (response) {
            "success" -> "Operation was successful"
            "error" -> "There was an error"
            else -> "Unknown status"
        }
    }
}
