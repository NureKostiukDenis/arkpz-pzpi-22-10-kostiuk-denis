package org.anware.core.exeptions

/**
 * Клас виключення, що виникає при проблемах із обробкою payload продукту.
 *
 * @param message Детальне повідомлення про помилку.
 * @param cause Первинна причина винятку.
 */
class ProductPayloadException(
    message: String,
    cause: Throwable? = null
) : RuntimeException(message, cause)