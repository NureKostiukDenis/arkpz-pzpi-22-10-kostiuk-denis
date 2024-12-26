package org.anware.drivers.config

import org.anware.presentation.handler.ApiKeyInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig(
    private val apiKeyInterceptor: ApiKeyInterceptor
) : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(apiKeyInterceptor)
            .addPathPatterns(*APIKEY_ENDPOINTS)
    }

    companion object{
        val APIKEY_ENDPOINTS = arrayOf("/api/sections/**", "/api/reports/**", "/api/items/**")
    }
}


