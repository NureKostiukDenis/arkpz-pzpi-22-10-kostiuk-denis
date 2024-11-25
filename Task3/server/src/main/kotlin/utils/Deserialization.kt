package org.anware.utils

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

object Deserialization {
    private val objectMapper = jacksonObjectMapper()

    operator fun <T> invoke(payload: String, clazz: Class<T>): T {
        return objectMapper.readValue(payload, clazz)
    }
}