package org.anware.data.service

interface ApiKey {
    fun generateApiKey(signature: ApiKeyGenerator.ApiKeySignature): String?
    fun validateApiKey(apiKey: String): Boolean
    fun extractDataFromApiKey(apiKey: String): ApiKeyGenerator.ApiKeySignature?
}