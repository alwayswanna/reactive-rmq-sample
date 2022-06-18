package a.gleb.receiverservice.configuration

import a.gleb.receiverservice.model.RequestModel
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import reactor.core.publisher.Sinks

@Configuration
class ReceiverServiceConfiguration {

    @Bean
    fun converter() : Jackson2JsonMessageConverter{
        return Jackson2JsonMessageConverter()
    }

    @Bean
    fun publisherRequestModel(): Sinks.Many<RequestModel>{
        return Sinks.many().multicast().onBackpressureBuffer()
    }
}