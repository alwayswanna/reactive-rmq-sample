package a.gleb.producerapp.configuration.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.web.cors.CorsConfiguration


@ConfigurationProperties("producer-options")
class ProducerConfigurationProperties {
    var connectionName: String = "default-connection"
    var queueBindingsMap: Map<String, QueueBinding> = mapOf()
    var exchangeBindingsMap: Map<String, ExchangeBinding> = mapOf()
    var bindingOptionsMap: Map<String, BindingOptions> = mapOf()
    var dlqQueueBindingsMap: Map<String, DlqQueueBinding> = mapOf()
    var cors: Cors = Cors()
}

class QueueBinding {
    var queueName: String? = null
    var dlqExchangeName: String? = null
    var dlqRoutingKey: String? = null
}

class DlqQueueBinding{
    var queueName: String? = null
}

class ExchangeBinding {
    var exchangeName: String? = null
}

class BindingOptions {
    var queueName: String? = null
    var exchangeName: String? = null
    var routingKey: String? = null
}

class Cors: CorsConfiguration()

