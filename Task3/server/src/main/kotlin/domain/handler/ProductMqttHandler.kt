package org.example.core.handlers

import org.anware.core.repostories.ProductRepository
import org.springframework.stereotype.Component

@Component
class ProductMqttHandler(private val productRepository: ProductRepository) : MqttMessageHandler {

    override fun handleMessage(topic: String, payload: String) {
        when (topic) {

        }
    }

}
