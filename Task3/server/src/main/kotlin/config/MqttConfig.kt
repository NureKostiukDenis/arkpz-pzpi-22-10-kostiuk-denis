package org.example.config

import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Конфігурація MQTT-клієнта для підключення до брокера MQTT.
 */
@Configuration
class MqttConfig {

    // URL брокера MQTT
    private lateinit var brokerUrl: String

    // Ідентифікатор клієнта MQTT
    private lateinit var clientId: String

    // Ім'я користувача для автентифікації
    private lateinit var userName: String

    // Пароль для автентифікації
    private lateinit var password: String

    /**
     * Налаштування підключення до брокера MQTT.
     *
     * @return Налаштовані параметри підключення.
     */
    @Bean
    fun mqttConnectOptions(): MqttConnectOptions {
        return MqttConnectOptions().apply {
            isCleanSession = true // Брокер не зберігає дані про поредені підписки
            userName = this@MqttConfig.userName
            password = this@MqttConfig.password.toCharArray()
            isAutomaticReconnect = true
            connectionTimeout = 30
            keepAliveInterval = 60 // Інтервал у секундах для перевірки підключення до брокера
        }
    }

    /**
     * Створює MQTT-клієнт з заданими параметрами підключення.
     *
     * @param mqttConnectOptions Параметри підключення, створені методом mqttConnectOptions().
     * @return підключенний MQTT-клієнт.
     */
    @Bean
    fun mqttClient(mqttConnectOptions: MqttConnectOptions): MqttClient {
        val client = MqttClient(brokerUrl, clientId, MemoryPersistence())
        client.connect(mqttConnectOptions) // Підключає клієнта до брокера
        return client
    }
}
