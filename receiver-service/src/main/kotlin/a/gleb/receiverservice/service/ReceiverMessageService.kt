package a.gleb.receiverservice.service

import a.gleb.receiverservice.exception.RabbitMessageProceedException
import a.gleb.receiverservice.model.RequestModel
import mu.KotlinLogging
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.cloud.stream.function.StreamBridge
import org.springframework.stereotype.Service
import java.lang.Boolean.TRUE
import java.time.LocalDateTime
import java.util.UUID.randomUUID

const val MESSAGE_STATUS_OUT = "message-status-out-0"
const val MESSAGE_IN_CHANNEL = "message.message-check"
const val TIME_ATTR = "time"
const val ID_ATTR = "id"
const val STATUS_ATTR = "status"

private val log = KotlinLogging.logger {}

@Service
class ReceiverMessageService(
    private val streamBridge: StreamBridge
) {

    @RabbitListener(queues = [MESSAGE_IN_CHANNEL])
    fun receiverMessage(message: RequestModel) {
        if (message.attributes?.isEmpty() != true) {
            message.attributes = mapOf(
                TIME_ATTR to LocalDateTime.now().toString(),
                ID_ATTR to randomUUID().toString(),
                STATUS_ATTR to TRUE.toString()
            )
        }
        log.info { "ReceiverMessageService, send message: $message" }
        if (!streamBridge.send(MESSAGE_STATUS_OUT, message)) {
            log.warn { "ReceiverMessageService, error while send message: $message" }
            throw RabbitMessageProceedException("ReceiverMessageService, can`t send message to rabbit: $message")
        }
    }
}
