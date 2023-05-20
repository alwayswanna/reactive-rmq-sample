package a.gleb.consumerapp.configuration

import a.gleb.consumerapp.configuration.properties.ConsumerConfigurationProperties
import a.gleb.consumerapp.logger
import a.gleb.consumerapp.service.MessageService
import com.rabbitmq.client.Connection
import com.rabbitmq.client.ConnectionFactory
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.springframework.boot.autoconfigure.amqp.RabbitProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import reactor.core.Disposable
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import reactor.rabbitmq.ConsumeOptions
import reactor.rabbitmq.ExceptionHandlers.CONNECTION_RECOVERY_PREDICATE
import reactor.rabbitmq.ExceptionHandlers.RetryAcknowledgmentExceptionHandler
import reactor.rabbitmq.RabbitFlux
import reactor.rabbitmq.Receiver
import reactor.rabbitmq.Receiver.AcknowledgmentContext
import reactor.rabbitmq.ReceiverOptions
import reactor.util.retry.RetrySpec
import java.time.Duration
import java.util.function.BiConsumer


val exceptionHandler: BiConsumer<AcknowledgmentContext, Exception> = RetryAcknowledgmentExceptionHandler(
    Duration.ofSeconds(20), Duration.ofMillis(500),
    CONNECTION_RECOVERY_PREDICATE
)

@Configuration
class RabbitConfig(
    private val properties: ConsumerConfigurationProperties
) {

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

    @Bean
    fun receive(connection: Mono<Connection>): Receiver {
        return RabbitFlux.createReceiver(
            ReceiverOptions()
                .connectionMono(connection)
                .connectionMonoConfigurator {
                    it.retryWhen(
                        RetrySpec.backoff(
                            properties.rabbitRetryer.attempts,
                            Duration.ofSeconds(properties.rabbitRetryer.timeout)
                        )
                    )
                }
                .connectionSubscriptionScheduler(Schedulers.boundedElastic())
        )
    }

    @OptIn(DelicateCoroutinesApi::class)
    @Bean
    fun receiveMessage(receiver: Receiver, messageService: MessageService): Disposable {
        return receiver
            .consumeManualAck("reactive-kt-rmq.message", ConsumeOptions().exceptionHandler(exceptionHandler))
            .subscribe { msg ->
                GlobalScope.launch { messageService.proceedMessage(msg) }.invokeOnCompletion {
                    if (it != null) {
                        logger.error { "Error while proceed user-message ${it.message}" }
                        msg.nack(false)
                    } else {
                        msg.ack()
                    }
                }
            }
    }
}