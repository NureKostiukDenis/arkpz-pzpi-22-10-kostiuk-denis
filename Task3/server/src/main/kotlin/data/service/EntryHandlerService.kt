package org.anware.data.service

import org.anware.domain.handler.ApiKeyInvalidException
import org.anware.domain.handler.MqttMessageHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class EntryHandlerService @Autowired constructor(
    private val apiKey: ApiKey
) : MqttMessageHandler {

    override fun handleMessage(topic: String, payload: String, warehouseApiKey: String?, gateId: String?) {
        if (warehouseApiKey == null) {
            throw ApiKeyInvalidException("Apikey is null")
        }

        val warehouseData: ApiKeyGenerator.ApiKeySignature = apiKey.extractDataFromApiKey(warehouseApiKey)
            ?: throw ApiKeyInvalidException("Apikey is invalid")

        // TODO()
    }

    private fun enterAction(){

    }

}