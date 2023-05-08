package a.gleb.producerapp.configuration

import com.rabbitmq.client.Connection
import jakarta.annotation.PreDestroy
import org.springframework.context.annotation.Configuration
import reactor.core.publisher.Mono

@Configuration
class RabbitConnectionConfig(
    private val connection: Mono<Connection>
) {

    /**
     * Method destroy connection on kill.
     */
    @PreDestroy
    fun destroy() {
        connection.block()?.close()
    }
}