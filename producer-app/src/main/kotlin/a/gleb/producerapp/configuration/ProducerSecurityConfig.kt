package a.gleb.producerapp.configuration

import a.gleb.producerapp.configuration.properties.ProducerConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.web.cors.reactive.CorsConfigurationSource
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource

@Configuration
class ProducerSecurityConfig {

    @Bean
    fun oauth2SecurityWebFilterChain(httpSecurity: ServerHttpSecurity, properties: ProducerConfigurationProperties):
            SecurityWebFilterChain {
        httpSecurity
            .anonymous().disable()
            .csrf().disable()
        httpSecurity.cors {
            it.configurationSource(corsConfigurationSource(properties))
        }

        return httpSecurity.build()
    }

    /**
     * Method generate CORS for microservice.
     */
    @Bean
    fun corsConfigurationSource(properties: ProducerConfigurationProperties): CorsConfigurationSource {
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", properties.cors)
        return source
    }
}