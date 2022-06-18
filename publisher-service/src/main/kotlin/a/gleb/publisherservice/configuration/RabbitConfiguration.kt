package a.gleb.publisherservice.configuration

import a.gleb.publisherservice.model.RequestModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import mu.KotlinLogging
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import reactor.core.publisher.Sinks

private val log = KotlinLogging.logger {}
@Configuration
class RabbitConfiguration {

    @Bean
    fun messageCheck(publisherRequestModel:  Sinks.Many<RequestModel>): suspend () -> Flow<RequestModel>{
        return {publisherRequestModel.asFlux().asFlow()}
    }

    @Bean
    fun messageStatus(): suspend (Flow<RequestModel>) -> Unit = {
            flow -> flow.collect { log.info { "Message from RabbitMQ: $it" } }
    }

    @Bean
    fun publisherRequestModel(): Sinks.Many<RequestModel> {
        return Sinks.many().multicast().onBackpressureBuffer()
    }
}