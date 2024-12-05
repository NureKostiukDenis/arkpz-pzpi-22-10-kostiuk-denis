package org.anware.data.service

import org.anware.core.apikey.ApiKeySignature
import org.anware.core.apikey.extractDataFromApiKey
import org.anware.domain.handler.ApiKeyInvalid
import org.anware.domain.handler.MqttMessageHandler
import org.springframework.stereotype.Service

@Service
class EntryHandlerService: MqttMessageHandler {

    override fun handleMessage(topic: String, payload: String, warehouseApiKey: String?, gateId: String?) {
        if (warehouseApiKey == null) {
            throw ApiKeyInvalid("Apikey is null")
        }

        val warehouseData: ApiKeySignature = extractDataFromApiKey(warehouseApiKey)
            ?: throw ApiKeyInvalid("Apikey is invalid")

        // TODO()
    }

    private fun enterAction(){

    }

}