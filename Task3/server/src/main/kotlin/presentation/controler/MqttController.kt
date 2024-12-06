package org.anware.presentation.controler

import org.anware.domain.handler.MqttMessageHandler
import org.springframework.integration.annotation.ServiceActivator
import org.springframework.stereotype.Component

@Component
class MqttController(private val handlers: List<MqttMessageHandler>) {

    @ServiceActivator(inputChannel = "mqttInputChannel")
    fun processMessage(payload: String, headers: Map<String, Any>) {
        val topic = headers["mqtt_receivedTopic"] as String? ?: ""
        if(!validateTopic(topic)){
            return
        }
        val warehouseAPIKey = extractWarehouseAPIKey(topic)
        val gateId = extractGateId(topic)

        handlers.forEach { handler ->
            handler.handleMessage(topic, payload, warehouseAPIKey, gateId)
        }
    }

    private fun extractWarehouseAPIKey(topic: String): String? {
        val regex = Regex("""warehouse/(\w+)/entry/.*""")
        return regex.find(topic)?.groups?.get(1)?.value
    }

    private fun extractGateId(topic: String): String? {
        val regex = Regex("""warehouse/.*/entry/(\w+).*""")
        return regex.find(topic)?.groups?.get(1)?.value
    }

    private fun validateTopic(topic: String): Boolean{
        return true
    }
}

