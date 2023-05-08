tasks.register("prepareKotlinBuildScriptModel"){}

plugins {
    id("org.springframework.boot")
    kotlin("jvm")
    kotlin("plugin.spring")
    kotlin("plugin.serialization")
}

val reactorRabbitMQVersion = "1.5.6"
val springDocOpenApiVersion = "2.1.0"
val kotlinMuLoggingVersion = "3.0.5"
val kotlinSerializationVersion = "1.5.1"

dependencies {
    /* spring */
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-amqp")

    /* other */
    implementation("io.projectreactor.rabbitmq:reactor-rabbitmq:$reactorRabbitMQVersion")
    implementation("org.springdoc:springdoc-openapi-starter-webflux-ui:$springDocOpenApiVersion")
    implementation("io.github.microutils:kotlin-logging-jvm:$kotlinMuLoggingVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlinSerializationVersion")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

    /* annotation processor */
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    /* test */
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
}
