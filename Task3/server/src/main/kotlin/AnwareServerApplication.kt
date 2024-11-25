package org.anware

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["org.anware.core.services", "org.example.drivers.config"])
class AnwareServerApplication

fun main(args: Array<String>) {
    runApplication<AnwareServerApplication>(*args)
}

