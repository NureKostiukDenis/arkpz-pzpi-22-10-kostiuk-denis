package org.anware.config

import com.google.firebase.auth.FirebaseAuth
import org.anware.data.dto.UserRole
import org.anware.data.repository.UserRepository
import org.anware.domain.usecase.UserUseCases
import org.anware.drivers.firebase.TokenAuthenticationFilter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig @Autowired constructor(
    val firebaseAuth: FirebaseAuth,
    val userRepository: UserRepository,
    val useCases: UserUseCases
) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        println(http)
        http
            .csrf { it.disable() }
            .authorizeHttpRequests {
                it.requestMatchers(HttpMethod.POST, *WHITELISTED_API_ENDPOINTS).permitAll()
                    .requestMatchers(*ADMIN_API_ENDPOINTS).hasRole(UserRole.ADMIN.toString())
                    .requestMatchers(*STAFF_API_ENDPOINTS).hasAnyRole(UserRole.STAFF.toString(), UserRole.ADMIN.toString())
                    .anyRequest().authenticated()
            }

            .addFilterBefore(TokenAuthenticationFilter(firebaseAuth, userRepository, useCases), UsernamePasswordAuthenticationFilter::class.java)
        return http.build()
    }

    companion object{
        val WHITELISTED_API_ENDPOINTS = arrayOf("/api/user/log-in", "/api/user/create")
        val ADMIN_API_ENDPOINTS = arrayOf("/api/warehouse/api-key/**", "/api/warehouse/gate-api-key", "api/gate/**")
        val STAFF_API_ENDPOINTS = arrayOf("/api/item/list-warehouse", "/api/item/list-warehouse-section", "/api/report/**")
        val WITHOUT_APIKEY = arrayOf("/api/user/log-in", "/api/user/create", "/api/warehouse/api-key", "/api/warehouse/create")
    }

}
