package a.gleb.producerapp.service

import a.gleb.producerapp.configuration.properties.ProducerConfigurationProperties
import a.gleb.producerapp.logger
import a.gleb.producerapp.model.UserMessage
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.rabbitmq.OutboundMessage
import reactor.rabbitmq.SendOptions
import reactor.rabbitmq.Sender

@Service
class SenderService(
    private val sender: Sender,
    private val properties: ProducerConfigurationProperties
) {

    /**
     * Method send message to queue with publish confirms
     */
    suspend fun sendMessage(message: UserMessage){
        val queueName = properties.queueBindingsMap["message"]?.queueName

        sender
            .sendWithPublishConfirms(
                Mono.just(message).map { s -> OutboundMessage("", queueName!!, Json.encodeToString(s).toByteArray()) },
                SendOptions().trackReturned(true)
            )
            .doOnError { logger.error { "Error while send message to queue $queueName, $it" } }
            .subscribe{
                if (it.isReturned){
                    logger.error { "Error while send message to queue $queueName" }
                }
            }
    }
}