package a.gleb.publisherservice.service

import a.gleb.publisherservice.model.RequestModel
import a.gleb.publisherservice.model.ResponseModel
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import reactor.core.publisher.Sinks
import java.time.LocalDateTime

private const val OUT_MESSAGE_CHANNEL = "message-check-out-0"
private val log = KotlinLogging.logger {}

@Service
class PublisherService(
    private val publisherRequestModel: Sinks.Many<RequestModel>
) {

    suspend fun publishMessage(request: RequestModel): ResponseModel {
        log.info { "publish message: $request" }
        publisherRequestModel.tryEmitNext(request).orThrow()
        return ResponseModel(
            HttpStatus.OK, "Success send to channel: $OUT_MESSAGE_CHANNEL",
            mapOf("time" to LocalDateTime.now().toString())
        )
    }
}