tasks.register("prepareKotlinBuildScriptModel"){}

plugins {
	id("org.springframework.boot")
	kotlin("jvm")
	kotlin("plugin.spring")
	kotlin("plugin.serialization")
}

val kotlinMuLoggingVersion = "3.0.5"
val reactorRabbitMQVersion = "1.5.6"
val kotlinSerializationVersion = "1.5.1"

dependencies {
	/* spring */
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("org.springframework.boot:spring-boot-starter-amqp")
	implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
	implementation("org.flywaydb:flyway-core")
	runtimeOnly("org.postgresql:r2dbc-postgresql")
	runtimeOnly("org.postgresql:postgresql")

	/* other */
	implementation("io.projectreactor.rabbitmq:reactor-rabbitmq:$reactorRabbitMQVersion")
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
