package org.anware.data.service

interface ApiKeyService <T> {
    fun generateApiKey(signature: T): String?
    fun validateApiKey(apiKey: String): Boolean
    fun extractDataFromApiKey(apiKey: String): T?
}