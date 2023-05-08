package a.gleb.consumerapp.configuration

import com.rabbitmq.client.Connection
import jakarta.annotation.PreDestroy
import org.springframework.context.annotation.Configuration
import reactor.core.publisher.Mono

@Configuration
class RabbitConnectionConfig(
    private val connection: Mono<Connection>
) {

    @PreDestroy
    fun close() {
        connection.block()?.close()
    }
}