package org.anware.drivers.config

import org.anware.domain.handler.MqttMessageHandler
import org.anware.presentation.controler.MqttController
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.integration.channel.DirectChannel
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory
import org.springframework.integration.mqtt.core.MqttPahoClientFactory
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter
import org.springframework.messaging.MessageChannel

@Configuration
class MqttConfig {

    @Value("\${mqtt.host}")
    lateinit var mqttHost: String

    @Value("\${mqtt.username}")
    lateinit var mqttUsername: String

    @Value("\${mqtt.password}")
    lateinit var mqttPassword: String

    @Bean
    fun mqttClientFactory(): MqttPahoClientFactory {
        val factory = DefaultMqttPahoClientFactory()
        factory.connectionOptions.apply {
            this.userName = mqttUsername
            this.password = mqttPassword.toCharArray()
            this.serverURIs = arrayOf(mqttHost)
        }
        return factory
    }

    @Bean
    fun mqttInputChannel(): MessageChannel = DirectChannel()

    @Bean
    fun mqttAdapter(mqttClientFactory: MqttPahoClientFactory, mqttInputChannel: MessageChannel): MqttPahoMessageDrivenChannelAdapter {
        val adapter = MqttPahoMessageDrivenChannelAdapter(
            "allah30000", mqttClientFactory, "warehouse/#"
        )
        adapter.outputChannel = mqttInputChannel
        adapter.setCompletionTimeout(5000)
        return adapter
    }

    @Bean
    fun mqttBrokerService(handlers: List<MqttMessageHandler>, mqttClientFactory: MqttPahoClientFactory): MqttController {
        return MqttController(handlers)
    }

}