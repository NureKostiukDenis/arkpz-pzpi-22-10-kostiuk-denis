package org.example.domain.handler

interface MqttMessageHandler {
    fun handleMessage(topic: String, payload: String)
}
