package a.gleb.producerapp.configuration

import a.gleb.producerapp.configuration.properties.ProducerConfigurationProperties
import com.rabbitmq.client.Connection
import com.rabbitmq.client.ConnectionFactory
import jakarta.annotation.PostConstruct
import org.springframework.amqp.core.*
import org.springframework.boot.autoconfigure.amqp.RabbitProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers.boundedElastic
import reactor.rabbitmq.RabbitFlux
import reactor.rabbitmq.Sender
import reactor.rabbitmq.SenderOptions

@Configuration
class RabbitConfig(
    private val amqpAdmin: AmqpAdmin,
    private val properties: ProducerConfigurationProperties
) {

    /**
     * @Bean create connection factory for app
     */
    @Bean
    fun connection(rabbitProperties: RabbitProperties): Mono<Connection> {
        val connectionFactory = ConnectionFactory().apply {
            host = rabbitProperties.host
            port = rabbitProperties.port
            username = rabbitProperties.username
            password = rabbitProperties.password
        }
        connectionFactory.useNio()

        return Mono.fromCallable { connectionFactory.newConnection(properties.connectionName) }.cache()
    }

    /**
     * @Bean create sender for app
     */
    @Bean
    fun sender(connection: Mono<Connection>): Sender {
        return RabbitFlux
            .createSender(SenderOptions().connectionMono(connection).resourceManagementScheduler(boundedElastic()))
    }

    /**
     * Method create exchanges, bindings, queues on start.
     * @see ProducerConfigurationProperties
     */
    @PostConstruct
    fun init() {
        /* create exchanges */
        properties.exchangeBindingsMap.values.forEach {
            amqpAdmin.declareExchange(ExchangeBuilder.directExchange(it.exchangeName).build())
        }

        /* create dlq-queues */
        properties.dlqQueueBindingsMap.values.forEach{
            amqpAdmin.declareQueue(Queue(it.queueName, true, false, false))
        }

        /* create queues */
        properties.queueBindingsMap.values.forEach {
            amqpAdmin.declareQueue(
                QueueBuilder.durable(it.queueName)
                    .deadLetterExchange(it.dlqExchangeName)
                    .deadLetterRoutingKey(it.dlqRoutingKey)
                    .build()
            )
        }

        /* bind queues to exchanges */
        properties.bindingOptionsMap.values.forEach {
            amqpAdmin.declareBinding(
                BindingBuilder
                    .bind(Queue(it.queueName))
                    .to(DirectExchange(it.exchangeName))
                    .with(it.routingKey)
            )
        }
    }
}