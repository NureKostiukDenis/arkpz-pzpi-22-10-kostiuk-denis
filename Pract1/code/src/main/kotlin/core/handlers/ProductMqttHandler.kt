package org.example.core.handlers

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.anware.core.exeptions.ProductPayloadException
import org.anware.core.extensions.toEntity
import org.anware.core.services.ProductService
import org.anware.database.models.ProductModel
import org.anware.dto.ProductPayload
import org.springframework.stereotype.Component

@Component
class ProductMqttHandler(private val productService: ProductService) : MqttMessageHandler {

    override fun handleMessage(topic: String, payload: String) {
        when (topic) {
            "warehouse/entry" -> handleProductEntry(payload)
            "warehouse/exit" -> handleProductExit(payload)
            else -> logUnknownTopic(topic)
        }
    }

    private fun handleProductEntry(payload: String) {
        val product = parseProductFromPayload(payload)
        productService.addProduct(product)
    }

    private fun handleProductExit(payload: String) {
        val productId = extractProductIdFromPayload(payload)
        productService.deleteProduct(productId)
    }

    private fun parseProductFromPayload(payload: String): ProductModel {
        return try {
            val productPayload: ProductPayload = deserializePayload(payload, ProductPayload::class.java)
            productPayload.toEntity()
        } catch (e: Exception) {
            throw ProductPayloadException("Error parsing product from payload", e)
        }
    }

    private fun extractProductIdFromPayload(payload: String): String {
        return try {
            val productPayload: ProductPayload = deserializePayload(payload, ProductPayload::class.java)
            productPayload.id.toString()
        } catch (e: Exception) {
            throw ProductPayloadException("Error extracting product ID from payload", e)
        }
    }

    private fun <T> deserializePayload(payload: String, clazz: Class<T>): T {
        val objectMapper = jacksonObjectMapper()
        return objectMapper.readValue(payload, clazz)
    }

    private fun logUnknownTopic(topic: String) {
        println("Unknown topic: $topic")
    }

}
