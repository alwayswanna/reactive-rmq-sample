package a.gleb.consumerapp.configuration

import a.gleb.consumerapp.configuration.properties.ConsumerConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(ConsumerConfigurationProperties::class)
class ConsumerConfig {
}