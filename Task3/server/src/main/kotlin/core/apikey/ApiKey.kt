package org.anware.core.apikey

import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

fun generateApiKey(signature: ApiKeySignature, secret: String): String {
    val data = signature.toString()
    val hmac = hmacSha256(data, secret)
    return Base64.getEncoder().encodeToString("$data:$hmac".toByteArray())
}

fun validateApiKey(apiKey: String, secret: String): Boolean {
    val decodedKey = String(Base64.getDecoder().decode(apiKey))
    val parts = decodedKey.split(":")
    if (parts.size != 4) return false

    val (warehouseName, warehouseId, type, signature) = parts
    val expectedSignature = hmacSha256("$warehouseName:$warehouseId:$type", secret)
    return expectedSignature == signature
}

fun extractDataFromApiKey(apiKey: String): ApiKeySignature? {
    val decodedKey = String(Base64.getDecoder().decode(apiKey))
    val parts = decodedKey.split(":")
    if (parts.size != 4) return null

    val (warehouseName, warehouseId, type, _) = parts
    return ApiKeySignature(
        warehouseId = warehouseId,
        warehouseName = warehouseName,
        type = Type.valueOf(type)
    )
}

fun hmacSha256(data: String, secret: String): String {
    val hmac = Mac.getInstance("HmacSHA256")
    hmac.init(SecretKeySpec(secret.toByteArray(), "HmacSHA256"))
    return Base64.getEncoder().encodeToString(hmac.doFinal(data.toByteArray()))
}

enum class Type {
    READ,
    WRITE
}

data class ApiKeySignature(
    val warehouseId: String,
    val warehouseName: String,
    val type: Type
) {
    override fun toString(): String {
        return "$warehouseName:$warehouseId:${type.name}"
    }
}
