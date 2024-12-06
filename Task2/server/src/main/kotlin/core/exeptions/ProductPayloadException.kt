package org.anware.core.exeptions

class ProductPayloadException(
    message: String,
    cause: Throwable? = null
) : RuntimeException(message, cause)