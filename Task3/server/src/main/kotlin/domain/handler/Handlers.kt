package org.example.core.handlers

interface MqttMessageHandler {
    fun handleMessage(topic: String, payload: String)
}
