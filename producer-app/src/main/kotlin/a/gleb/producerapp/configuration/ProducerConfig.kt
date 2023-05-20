package a.gleb.producerapp.configuration

import a.gleb.producerapp.configuration.properties.ProducerConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(ProducerConfigurationProperties::class)
class ProducerConfig {
}