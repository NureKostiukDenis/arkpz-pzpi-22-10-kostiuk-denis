package org.anware.core.services

import jakarta.annotation.PostConstruct
import org.eclipse.paho.client.mqttv3.IMqttMessageListener
import org.eclipse.paho.client.mqttv3.MqttClient
import org.example.core.handlers.MqttMessageHandler
import org.springframework.stereotype.Service

@Service
class MqttBrokerService(
    private val mqttClient: MqttClient,
    private val handlers: List<MqttMessageHandler>
    ) {

    @PostConstruct
    fun init() {
        mqttClient.subscribe("warehouse/entry/#", IMqttMessageListener { topic, message ->
            notifyHandlers(topic, String(message.payload))
        })
    }

    private fun notifyHandlers(topic: String, payload: String) {
        handlers.forEach { handler -> handler.handleMessage(topic, payload) }
    }
}
