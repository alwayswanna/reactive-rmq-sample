package a.gleb.receiverservice.configuration

import a.gleb.receiverservice.model.RequestModel
import a.gleb.receiverservice.service.ReceiverMessageService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import reactor.core.publisher.Sinks

@Configuration
class RabbitConfiguration(
    private val receiverMessageService: ReceiverMessageService
) {

    @Bean
    fun messageCheck(): suspend (Flow<RequestModel>) -> Unit = {
            flow -> flow.collect { receiverMessageService.proceed(it) }
    }

    @Bean
    fun messageStatus(publisherRequestModel: Sinks.Many<RequestModel>):suspend () -> Flow<RequestModel>{
        return {publisherRequestModel.asFlux().asFlow()}
    }
}