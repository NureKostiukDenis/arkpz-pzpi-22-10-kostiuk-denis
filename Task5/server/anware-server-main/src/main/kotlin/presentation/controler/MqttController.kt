package org.anware.presentation.controler

import org.anware.data.service.Logger
import org.anware.domain.handler.MqttMessageHandler
import org.springframework.integration.annotation.ServiceActivator
import org.springframework.stereotype.Component

@Component
class MqttController(
    private val handlers: List<MqttMessageHandler>
): Logger() {

    @ServiceActivator(inputChannel = "mqttInputChannel")
    fun processMessage(payload: String, headers: Map<String, Any>) {
        val topic = headers["mqtt_receivedTopic"] as String? ?: ""
        logger.info(topic)
        if(!validateTopic(topic)){
            return
        }
        val gateAPIKey = extractGateAPIKey(topic)
        val gateCode = extractGateCode(topic)

        handlers.forEach { handler ->
            handler.handleMessage(topic, payload, gateAPIKey, gateCode)
        }
    }

    private fun extractGateAPIKey(topic: String): String? {
        val regex = Regex("""warehouse/([^/]+)/entry/.*""")
        return regex.find(topic)?.groups?.get(1)?.value
    }

    private fun extractGateCode(topic: String): String? {
        val regex = Regex("""warehouse/[^/]+/entry/([^/]+)""")
        return regex.find(topic)?.groups?.get(1)?.value
    }

    private fun validateTopic(topic: String): Boolean {
        val regexEntry = Regex("""warehouse/([^/]+)/entry/([^/]+)""")
        val regexLog = Regex("""warehouse/([^/]+)/entry/([^/]+)""")
        return regexEntry.matches(topic)
    }

    enum class TopicType{
        ENTRY, LOG
    }
}

