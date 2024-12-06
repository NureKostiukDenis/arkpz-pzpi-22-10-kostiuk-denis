package org.anware.data.controler

import jakarta.annotation.PostConstruct
import org.example.core.handlers.MqttMessageHandler
import org.springframework.integration.annotation.ServiceActivator
import org.springframework.integration.channel.DirectChannel
import org.springframework.integration.mqtt.core.MqttPahoClientFactory
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter
import org.springframework.messaging.MessageChannel
import org.springframework.stereotype.Service

@Service
class MqttBrokerService(
    private val handlers: List<MqttMessageHandler>,
    private val mqttClientFactory: MqttPahoClientFactory
) {

    private val inputChannel: MessageChannel = DirectChannel()

    @PostConstruct
    fun init() {
        val mqttAdapter = MqttPahoMessageDrivenChannelAdapter(
            "mqttClientId", mqttClientFactory, "warehouse/entry/#"
        )
        mqttAdapter.setOutputChannel(inputChannel)
    }

    @ServiceActivator(inputChannel = "inputChannel")
    fun handleMessage(payload: String) {
        handlers.forEach { handler ->
            handler.handleMessage("warehouse/entry/#", payload)
        }
    }
}