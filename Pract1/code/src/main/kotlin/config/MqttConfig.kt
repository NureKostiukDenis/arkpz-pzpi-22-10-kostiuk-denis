package org.example.config

import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MqttConfig {

    private lateinit var brokerUrl: String
    private lateinit var clientId: String
    private lateinit var userName: String
    private lateinit var password: String

    @Bean
    fun getMqttConnectOptions(): MqttConnectOptions {
        return MqttConnectOptions().apply {
            isCleanSession = true // Брокер не зберігає дані про поредені підписки
            userName = this@MqttConfig.userName
            password = this@MqttConfig.password.toCharArray()
            isAutomaticReconnect = true
            connectionTimeout = 30
            keepAliveInterval = 60 // Інтервал у секундах для перевірки підключення до брокера
        }
    }



    @Bean
    fun getMqttClient(mqttConnectOptions: MqttConnectOptions): MqttClient {
        val client = MqttClient(brokerUrl, clientId, MemoryPersistence())
        client.connect(mqttConnectOptions) // Підключає клієнта до брокера
        return client
    }
}
