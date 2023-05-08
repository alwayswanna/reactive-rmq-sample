package a.gleb.consumerapp.configuration.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("consumer-options")
class ConsumerConfigurationProperties {
    var connectionName: String = "default-connection"
    var rabbitRetryer: RabbitRetryer = RabbitRetryer()
}

class RabbitRetryer{
    var attempts: Long = 3
    val timeout: Long = 10
}