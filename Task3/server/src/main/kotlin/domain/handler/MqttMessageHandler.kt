package org.anware.domain.handler

interface MqttMessageHandler {
    fun handleMessage(topic: String, payload: String, warehouseApiKey: String?, gateId: String?)
}

class ApiKeyInvalidException(msg: String?): Exception(msg)