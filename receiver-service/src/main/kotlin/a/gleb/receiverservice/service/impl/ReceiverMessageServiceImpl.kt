package a.gleb.receiverservice.service.impl

import a.gleb.receiverservice.exception.RabbitMessageProceedException
import a.gleb.receiverservice.model.RequestModel
import a.gleb.receiverservice.service.ReceiverMessageService
import mu.KotlinLogging
import org.springframework.stereotype.Service
import reactor.core.publisher.Sinks
import java.lang.Boolean.TRUE
import java.time.LocalDateTime
import java.util.UUID.randomUUID

private val log = KotlinLogging.logger{}
const val TIME_ATTR = "time"
const val ID_ATTR = "id"
const val STATUS_ATTR = "status"

@Service
class ReceiverMessageServiceImpl(
    private val publisherRequestModel: Sinks.Many<RequestModel>
) : ReceiverMessageService {

    override suspend fun proceed(message: RequestModel) {
        log.info { "Start proceed message: $message" }
        message.attributes = mapOf(
            TIME_ATTR to LocalDateTime.now().toString(),
            ID_ATTR to randomUUID().toString(),
            STATUS_ATTR to TRUE.toString()
        )
        publisherRequestModel.tryEmitNext(message).orThrowWithCause(
            RabbitMessageProceedException("Error while send message, time: ${LocalDateTime.now()}, message: $message")
        )
        log.info { "End proceed message: $message" }
    }
}