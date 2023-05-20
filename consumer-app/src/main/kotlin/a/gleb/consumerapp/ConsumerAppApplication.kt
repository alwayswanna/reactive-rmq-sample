package a.gleb.consumerapp

import mu.KotlinLogging
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

val logger = KotlinLogging.logger{}

@SpringBootApplication
class ConsumerAppApplication

fun main(args: Array<String>) {
	runApplication<ConsumerAppApplication>(*args)
}
