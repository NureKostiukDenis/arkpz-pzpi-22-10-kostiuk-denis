package org.example.core.handlers

/**
 * Інтерфейс обробки MQTT-повідомлень.
 * Реалізація цього інтерфейсу дозволяє класу обробляти повідомлення,
 * отримані з різних MQTT-тем.
 */
interface MqttMessageHandler {

    /**
     * Обробляє повідомлення, отримане з вказаної теми.
     *
     * @param topic Тема MQTT, з якої надійшло повідомлення.
     * @param payload Вміст повідомлення у вигляді рядка.
     */
    fun handleMessage(topic: String, payload: String)
}
