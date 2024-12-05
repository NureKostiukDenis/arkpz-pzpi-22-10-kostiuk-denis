package org.anware.domain.handler

interface MqttMessageHandler {
    fun handleMessage(topic: String, payload: String, warehouseApiKey: String?, gateId: String?)
}

class ApiKeyInvalid(msg: String?): Exception(msg)