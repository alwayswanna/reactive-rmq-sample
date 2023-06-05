package a.gleb.producerapp.controller

import a.gleb.producerapp.model.UserMessage
import a.gleb.producerapp.service.FileService
import a.gleb.producerapp.service.SenderService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE
import org.springframework.http.codec.multipart.FilePart
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/data")
@Tag(name = "producer-controller")
class UserMessageController(
    private val senderService: SenderService,
    private val fileService: FileService
) {

    @Operation(
        summary = "Send message to RabbitMQ"
    )
    @PostMapping("/message")
    suspend fun sendMessage(@RequestBody message: UserMessage) {
        senderService.sendMessage(message)
    }

    @Operation(
        summary = "Data from xlsx file to RabbitMQ"
    )
    @PostMapping("/file", consumes = [MULTIPART_FORM_DATA_VALUE])
    suspend fun sendFile(@RequestPart file: FilePart) {
        fileService.proceedAndSend(file)
    }
}