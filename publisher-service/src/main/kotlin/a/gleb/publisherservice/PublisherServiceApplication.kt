package a.gleb.publisherservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PublisherServiceApplication

fun main(args: Array<String>) {
    runApplication<PublisherServiceApplication>(*args)
}
