package org.anware.presentation.handler

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.anware.data.service.WarehouseApiKeyService
import org.anware.data.service.WarehouseService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor

@Component
class ApiKeyInterceptor @Autowired constructor(
    private val apiKeyServiceGenerator: WarehouseApiKeyService,
    private val warehouseService: WarehouseService
) : HandlerInterceptor {

    override fun preHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any
    ): Boolean {
        val apiKey = request.getHeader("ApiKey")

        if (apiKey == null || !apiKeyServiceGenerator.validateApiKey(apiKey)) {
            response.status = HttpServletResponse.SC_UNAUTHORIZED
            response.writer.write("Invalid API Key")
            return false
        }
        warehouseService.getByAPIKey(apiKey)
            ?: return false

        return true
    }
}