package a.gleb.consumerapp.service

import a.gleb.consumerapp.db.entity.UserMessageDAO
import a.gleb.consumerapp.db.repository.UserMessageRepository
import a.gleb.consumerapp.logger
import a.gleb.consumerapp.model.UserMessage
import com.rabbitmq.client.Delivery
import kotlinx.serialization.json.Json
import org.springframework.stereotype.Service
import java.nio.charset.Charset

@Service
class MessageService(
    private val userMessageRepository: UserMessageRepository
) {

    suspend fun proceedMessage(message: Delivery) {
        logger.info { "Start proceed message" }

        val payload = message.body.toString(Charset.defaultCharset())
        val userMessage = Json.decodeFromString<UserMessage>(payload)

        val savedUserMessage = userMessageRepository.save(
            UserMessageDAO(username = userMessage.username, message = userMessage.message)
        )

        logger.info { "End proceed message, saved id [id=${savedUserMessage.id}]" }
    }
}