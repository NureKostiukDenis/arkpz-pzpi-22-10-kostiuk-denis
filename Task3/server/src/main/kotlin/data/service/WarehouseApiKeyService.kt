package org.anware.data.service

import org.anware.data.repository.WarehouseRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

@Service
class WarehouseApiKeyService: ApiKeyService<WarehouseApiKeyService.WarehouseApiKeySignature> {

    @Value("\${server.apikey.secret}")
    lateinit var standartSecret: String

    @Autowired
    private lateinit var warehouseRepository: WarehouseRepository

    override fun generateApiKey(signature: WarehouseApiKeySignature): String? {
        val data = signature.toString()
        val secret = signature.warehousePassword ?: standartSecret
        val hmac = hmacSha256(data, secret)
        return Base64.getEncoder().encodeToString("$data:$hmac".toByteArray())
    }

    override fun validateApiKey(apiKey: String): Boolean {
        val decodedKey = String(Base64.getDecoder().decode(apiKey))
        val parts = decodedKey.split(":")
        if (parts.size != 4) return false

        val (warehouseName, warehouseId, type, signature) = parts
        val signatureToValidate = "$warehouseName:$warehouseId:$type"

        val secret = findSecretByWarehouse(warehouseId) ?: standartSecret
        val expectedSignature = hmacSha256(signatureToValidate, secret)

        return expectedSignature == signature
    }


    override fun extractDataFromApiKey(apiKey: String): WarehouseApiKeySignature? {
        val decodedKey = String(Base64.getDecoder().decode(apiKey))
        val parts = decodedKey.split(":")
        if (parts.size != 4) return null

        val (warehouseName, warehouseId, type, _) = parts

        return WarehouseApiKeySignature(
            warehouseId = warehouseId,
            warehouseName = warehouseName,
            warehousePassword = null,
            type = Type.valueOf(type)
        )
    }

    private fun findSecretByWarehouse(warehouseId: String): String? {
        return warehouseRepository.getReferenceById(warehouseId.toInt()).password
    }


    private fun hmacSha256(data: String, secret: String): String {
        val hmac = Mac.getInstance("HmacSHA256")
        hmac.init(SecretKeySpec(secret.toByteArray(), "HmacSHA256"))
        return Base64.getEncoder().encodeToString(hmac.doFinal(data.toByteArray()))
    }

    enum class Type {
        READ,
        WRITE
    }

    data class WarehouseApiKeySignature(
        val warehouseId: String,
        val warehouseName: String,
        val warehousePassword: String?,
        val type: Type
    ) {
        override fun toString(): String {
            return "$warehouseName:$warehouseId:${type.name}"
        }
    }

}
