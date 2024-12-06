package org.anware.config

import com.google.firebase.auth.FirebaseAuth
import org.anware.data.repository.UserRepository
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
    val userRepository: UserRepository
) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .authorizeHttpRequests {
                it.requestMatchers(HttpMethod.POST, *WHITELISTED_API_ENDPOINTS).permitAll()
                .requestMatchers(*ADMIN_API_ENDPOINTS).hasRole(Roles.ADMIN.toString())
                .anyRequest().authenticated()
            }
            .addFilterBefore(TokenAuthenticationFilter(firebaseAuth, userRepository), UsernamePasswordAuthenticationFilter::class.java)
        return http.build()
    }

    companion object{
        val WHITELISTED_API_ENDPOINTS = arrayOf("/api/user/log-in", "/api/user/create")
        val ADMIN_API_ENDPOINTS = arrayOf("/api/warehouse/get-api-key")
    }

    enum class Roles{
        ADMIN,
        USER
    }
}
