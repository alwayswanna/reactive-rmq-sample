package a.gleb.producerapp

import mu.KotlinLogging
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

val logger = KotlinLogging.logger{}

@SpringBootApplication
class ProducerAppApplication

fun main(args: Array<String>) {
    runApplication<ProducerAppApplication>(*args)
}
