package a.gleb.publisherservice.service

import a.gleb.publisherservice.model.RequestModel
import a.gleb.publisherservice.model.ResponseModel
import mu.KotlinLogging
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.cloud.stream.function.StreamBridge
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.time.LocalDateTime

private const val MESSAGE_IN_CHANNEL = "message.message-status"
private const val OUT_MESSAGE_CHANNEL = "message-check-out-0"
private const val ID = "id"
private val log = KotlinLogging.logger {}

@Service
class PublisherService(
    private val streamBridge: StreamBridge
) {

    suspend fun publishMessage(request: RequestModel): ResponseModel {
        log.info { "publish message: $request" }
        if (streamBridge.send(OUT_MESSAGE_CHANNEL, request)) {
            return ResponseModel(
                HttpStatus.OK, "Success send to channel: $OUT_MESSAGE_CHANNEL",
                mapOf("time" to LocalDateTime.now().toString())
            )
        }
        return ResponseModel(
            HttpStatus.BAD_REQUEST, "Failure send to channel: $OUT_MESSAGE_CHANNEL",
            mapOf("time" to LocalDateTime.now().toString())
        )
    }

    @RabbitListener(queues = [MESSAGE_IN_CHANNEL])
    fun listenMessage(message: RequestModel) {
        log.info { "New message: $message" }
    }
}