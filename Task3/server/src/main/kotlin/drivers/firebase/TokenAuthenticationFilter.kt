package org.anware.drivers.firebase

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.common.base.Optional
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.anware.config.SecurityConfig.Companion.WHITELISTED_API_ENDPOINTS
import org.anware.data.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ProblemDetail
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
internal class TokenAuthenticationFilter constructor(
    val firebaseAuth: FirebaseAuth,
    val userRepository: UserRepository
): OncePerRequestFilter() {

    private val objectMapper = ObjectMapper()

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {

            if (WHITELISTED_API_ENDPOINTS.any { request.requestURI.contains(it) }) {
                filterChain.doFilter(request, response)
                return
            }

            val authorizationHeader = request.getHeader(AUTHORIZATION_HEADER)

            if (authorizationHeader.isNullOrBlank()) {
                logger.warn("No Authorization header found")
                setAuthErrorDetails(response, "No Authorization header")
                return
            }

            if (!authorizationHeader.startsWith(BEARER_PREFIX)) {
                logger.warn("Invalid Authorization header format")
                setAuthErrorDetails(response, "Invalid Authorization header format")
                return
            }

            val token = authorizationHeader.replace(BEARER_PREFIX, "").trim()

            try {
                val firebaseToken = firebaseAuth.verifyIdToken(token)

                val userId = firebaseToken.claims[USER_ID_CLAIM]?.toString()
                if (userId.isNullOrBlank()) {
                    logger.warn("No user ID found in token")
                    setAuthErrorDetails(response, "Invalid token: No user ID")
                    return
                }

                val roles = getRolesForUser(userId)
                val authorities = listOf(SimpleGrantedAuthority("ROLE_$roles"))

                val authentication = UsernamePasswordAuthenticationToken(userId, null, authorities)
                authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = authentication

                logger.info("Authentication successful for user: $userId")
            } catch (authException: FirebaseAuthException) {
                logger.error("Firebase Authentication Error", authException)
                setAuthErrorDetails(response, "Invalid or expired token")
                return
            }

            filterChain.doFilter(request, response)
        } catch (e: Exception) {
            logger.error("Unexpected error in authentication filter", e)
            setAuthErrorDetails(response, "Unexpected authentication error")
        }
    }

    private fun getRolesForUser(uid: String): String? {
        return userRepository.findByUid(uid)?.role
    }

    private fun setAuthErrorDetails(
        response: HttpServletResponse,
        detail: String = "Authentication failure"
    ) {
        val unauthorized = HttpStatus.UNAUTHORIZED
        response.status = unauthorized.value()
        response.contentType = MediaType.APPLICATION_JSON_VALUE

        val problemDetail = ProblemDetail.forStatusAndDetail(
            unauthorized,
            detail
        )

        response.writer.write(objectMapper.writeValueAsString(problemDetail))
        response.writer.flush()
    }

    companion object {
        private const val BEARER_PREFIX = "Bearer "
        private const val USER_ID_CLAIM = "user_id"
        private const val AUTHORIZATION_HEADER = "Authorization"
    }
}