package a.gleb.publisherservice.contoller

import a.gleb.publisherservice.model.RequestModel
import a.gleb.publisherservice.model.ResponseModel
import a.gleb.publisherservice.service.PublisherService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import java.util.*

private const val PUBLISHER_CONTROLLER_TAG = "publisher-controller"

@Controller
@RequestMapping("/api/v1")
@Tag(name = PUBLISHER_CONTROLLER_TAG)
class PublisherController(
    private val publisherService: PublisherService) {

    @Operation(
        summary = "Send message method",
        tags = [PUBLISHER_CONTROLLER_TAG]
    )
    @PostMapping("/send-message-rabbit")
    suspend fun sendMessage(@RequestBody messageRequest: RequestModel) : ResponseEntity<ResponseModel>{
        return ResponseEntity.of(Optional.of(publisherService.publishMessage(messageRequest)))
    }
}