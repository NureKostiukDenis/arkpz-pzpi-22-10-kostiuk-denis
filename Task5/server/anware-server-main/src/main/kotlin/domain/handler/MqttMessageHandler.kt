package org.anware.domain.handler

interface MqttMessageHandler {
    fun handleMessage(topic: String, payload: String, gateApiKey: String?, gateCode: String?)
}

class ApiKeyInvalidException(msg: String?): Exception(msg)