package org.anware.data.service.log

import org.anware.domain.handler.MqttMessageHandler
import org.springframework.stereotype.Service

@Service
class MqttMessageHandlerDecorator: MqttMessageHandler {

    override fun handleMessage(topic: String, payload: String, gateApiKey: String?, gateId: String?) {
        println("Message from: $topic, payload: $payload")
    }

}