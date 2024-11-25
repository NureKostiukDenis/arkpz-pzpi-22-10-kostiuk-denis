package org.example.config

import org.anware.data.controler.MqttBrokerService
import org.example.core.handlers.MqttMessageHandler
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
    fun inputChannel(): MessageChannel = DirectChannel()

    @Bean
    fun mqttAdapter(mqttClientFactory: MqttPahoClientFactory, inputChannel: MessageChannel): MqttPahoMessageDrivenChannelAdapter {
        val mqttAdapter = MqttPahoMessageDrivenChannelAdapter("mqttClientId", mqttClientFactory, "warehouse/entry/#")
        mqttAdapter.setOutputChannel(inputChannel)
        return mqttAdapter
    }

    @Bean
    fun mqttBrokerService(handlers: List<MqttMessageHandler>, mqttClientFactory: MqttPahoClientFactory): MqttBrokerService {
        return MqttBrokerService(handlers, mqttClientFactory)
    }

}