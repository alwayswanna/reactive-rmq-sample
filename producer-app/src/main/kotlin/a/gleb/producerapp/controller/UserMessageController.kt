package a.gleb.producerapp.controller

import a.gleb.producerapp.model.UserMessage
import a.gleb.producerapp.service.SenderService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/data")
@Tag(name = "producer-controller")
class UserMessageController(
    private val senderService: SenderService
) {

    @Operation(
        summary = "Send message to RabbitMQ"
    )
    @PostMapping("/message")
    suspend fun sendMessage(@RequestBody message: UserMessage){
        senderService.sendMessage(message)
    }
}